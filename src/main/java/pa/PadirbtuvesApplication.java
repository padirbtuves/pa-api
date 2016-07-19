/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pa;

import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import pa.domain.statement.StatementDocument;
import pa.ui.InMemoryMessageRespository;
import pa.ui.Message;
import pa.ui.MessageRepository;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class PadirbtuvesApplication {
	
	private static final Logger LOG = Logger.getLogger(PadirbtuvesApplication.class.getName());

	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRespository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}

	@Bean
	public Unmarshaller paymentUnmarshaller() throws JAXBException {
		javax.xml.bind.JAXBContext jaxbContext = JAXBContext.newInstance(StatementDocument.class);
		LOG.info("JAXBContext created " + jaxbContext);
		return jaxbContext.createUnmarshaller();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PadirbtuvesApplication.class, args);
	}

}
