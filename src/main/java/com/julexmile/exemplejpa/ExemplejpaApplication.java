package com.julexmile.exemplejpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.julexmile.exemplejpa.data.DocteurRepository;
import com.julexmile.exemplejpa.models.Docteur;

@SpringBootApplication
public class ExemplejpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemplejpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner data_loader(DocteurRepository repo_docteur) {

		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				Docteur d1 = new Docteur("jeanbon", "Jean", "Bon", "Montreal");
				Docteur d2 = new Docteur("morteadele", "Morte", "Adèle", "Montreal");
				Docteur d3 = new Docteur("pepperronnie", "Pepper", "Ronnie", "Montreal");
				repo_docteur.save(d1);
				repo_docteur.save(d2);
				repo_docteur.save(d3);
			}
		};
	}
}
