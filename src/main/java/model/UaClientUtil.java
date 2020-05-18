package model;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import static java.util.Collections.singletonList;
import static com.google.common.collect.Lists.newArrayList;
import static java.time.Duration.ofMinutes;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.identity.IdentityProvider;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.elements.BinaryActuator;

/**
 * Just a Util class that contains some methods to easily use Milo's OPC UA Client (e.g. creating a client, writing nodes, subscribing to changes)
 */
public class UaClientUtil {

	private final static Logger logger = LoggerFactory.getLogger(UaClientUtil.class);
	private static IdentityProvider identityProvider = new AnonymousProvider();
	private static final AtomicInteger clientHandles = new AtomicInteger();

	
	/**
	 * Creates an OPC UA client for a given endpoint
	 * 
	 * @param endpointURL Complete URL to an endpoint
	 * @return An instance of {@link OpcUaClient} that can be used to read values
	 * @throws Exception
	 */
	public static OpcUaClient createClient(String endpointURL) throws Exception {

		List<EndpointDescription> endpoints;

		try {
			endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
		} catch (Throwable ex) {
			// try the explicit discovery endpoint as well
			String discoveryUrl = endpointURL;

			if (!discoveryUrl.endsWith("/")) {
				discoveryUrl += "/";
			}
			discoveryUrl += "discovery";

			logger.info("Trying explicit discovery URL: {}", discoveryUrl);
			endpoints = DiscoveryClient.getEndpoints(discoveryUrl).get();
		}

		EndpointDescription endpoint = endpoints.stream().filter(e -> true).findFirst()
				.orElseThrow(() -> new Exception("no desired endpoints returned"));

		logger.info("Using endpoint: {}", endpointURL);

		OpcUaClientConfig config = OpcUaClientConfig.builder().setApplicationName(LocalizedText.english("ft-lab opc ua client"))
				.setApplicationUri("urn:hsu-aut:ft-lab:client").setEndpoint(endpoint).setIdentityProvider(identityProvider)
				.setRequestTimeout(uint(5000)).build();

		return OpcUaClient.create(config);
	}

	

	/**
	 * Write a boolean value to a node
	 * 
	 * @param nodeId NodeId of the node to write to
	 * @param value Value to write to
	 * @return true, if writing successful. False otherwise.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static boolean writeBooleanNode(OpcUaClient client, NodeId nodeId, boolean value) throws InterruptedException, ExecutionException {
		Variant v = new Variant(value);

		// don't write status or timestamps
		DataValue dv = new DataValue(v, null, null);

		// write asynchronously....
		CompletableFuture<StatusCode> futureStatus = client.writeValue(nodeId, dv);

		// ...but block to get result
		StatusCode status = futureStatus.get();

		if (status.isGood()) {
			logger.info("Wrote '{}' to nodeId={}", v, nodeId);
			return true;
		}

		return false;
	}

	
	/**
	 * Sets up a subscription to a certain actuator
	 * @param client OPC UA client instance to use
	 * @param actuator A binary actuator whose value should be monitored 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void setupSubscription(OpcUaClient client, BinaryActuator actuator) throws InterruptedException, ExecutionException {

		// create a subscription @ 200ms
		UaSubscription subscription = client.getSubscriptionManager().createSubscription(200.0).get();

		// subscribe to the given actuators nodeId
		ReadValueId readValueId = new ReadValueId(actuator.getNodeId(), AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE);

		// IMPORTANT: client handle must be unique per item within the context of a subscription.
		// You are not required to use the UaSubscription's client handle sequence; it is provided as a convenience.
		// Your application is free to assign client handles by whatever means necessary.
		UInteger clientHandle = subscription.nextClientHandle();

		MonitoringParameters parameters = new MonitoringParameters(clientHandle, 200.0, // sampling interval
				null, // filter, null means use default
				uint(10), // queue size
				true // discard oldest
		);

		MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

		// Setting up the method that is called on value changes actuator::onValueChanged by passing a reference to this function
		BiConsumer<UaMonitoredItem, Integer> onItemCreated = (item, id) -> item.setValueConsumer(actuator::onValueChanged);

		// Creating monitored items (milo only allows creating a list of items, that's why the lists are necessary here)
		List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, newArrayList(request), onItemCreated).get();

		for (UaMonitoredItem item : items) {
			if (item.getStatusCode().isGood()) {
				logger.info("item created for nodeId={}", item.getReadValueId().getNodeId());
			} else {
				logger.warn("failed to create item for nodeId={} (status={})", item.getReadValueId().getNodeId(), item.getStatusCode());
			}
		}

	}
}