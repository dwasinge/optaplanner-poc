package pocs.optaplanner.delivery.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.SolverServicesClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kie.server.client")
public class KieServerClientConfig {

	private static final String KIE_SERVER_CONTENT_TYPE = "X-KIE-ContentType";

	private String url;
	private String user;
	private String password;
	private long timeout;
	private String marshallingFormat = "xstream";
	private String extraClasses;

	@Bean
	public SolverServicesClient solverServicesClient() throws ClassNotFoundException {

		// create kie services config
		KieServicesConfiguration conf = KieServicesFactory.newRestConfiguration(url, user, password, timeout);

		// set marshalling format
		MarshallingFormat format = MarshallingFormat.fromType(marshallingFormat);
		conf.setMarshallingFormat(format);

		// Set POJO classes for the Marshaller
		conf.setExtraClasses(createExtraClassHash());

		// Settig KIE Content Type for the server side Content-Type header
		Map<String, String> headers = new HashMap<>();
		headers.put(KIE_SERVER_CONTENT_TYPE, format.getType());
		conf.setHeaders(headers);

		// create kie services client using configuration
		KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(conf);

		// create solver services client
		return kieServicesClient.getServicesClient(SolverServicesClient.class);

	}

	private HashSet<Class<?>> createExtraClassHash() throws ClassNotFoundException {

		HashSet<Class<?>> extraClassesSet = new HashSet<>();

		if (null != extraClasses) {

			String[] split = extraClasses.split(",");

			for (String className : split) {
				extraClassesSet.add(Class.forName(className));
			}

		}

		return extraClassesSet;

	}

}
