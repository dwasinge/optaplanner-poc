package pocs.optaplanner.delivery.camel;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class SkillsAggregationStrategy implements AggregationStrategy {


	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

		oldExchange.getIn().setHeader("skills-reponse", newExchange.getIn().getBody(String.class));
		return oldExchange;

	}

}
