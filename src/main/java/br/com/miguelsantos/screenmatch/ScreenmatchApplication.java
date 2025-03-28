package br.com.miguelsantos.screenmatch;

import br.com.miguelsantos.screenmatch.model.DataSeries;
import br.com.miguelsantos.screenmatch.service.ConsumeApi;
import br.com.miguelsantos.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var api = new ConsumeApi();
		var json = api.obtainData("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");

		ConvertData converter = new ConvertData();
		DataSeries data = converter.convertData(json, DataSeries.class);
		System.out.println(data);
	}

}
