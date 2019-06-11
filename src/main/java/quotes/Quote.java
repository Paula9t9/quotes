package quotes;

import com.google.gson.Gson;
import com.sun.javafx.binding.StringFormatter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Quote {
    private String author;
    private String text;
    private String starWarsQuote;

    private Quote[] quotes;

    // Gets a random quote from the swquotes API. If that fails, it will pull a random quote from out file
    public String getQuote(){

        try{
            // Get starwars quote from starwars API
            URL url = new URL("http://swquotesapi.digitaljedi.dk/api/SWQuote/RandomStarWarsQuote");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader internetReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            Gson gson = new Gson();
            Quote newQuote = gson.fromJson(internetReader, Quote.class);
            return newQuote.starWarsQuote;

        }catch (IOException e){
            System.out.println("Error connecting to API");
            System.out.println(e);

            // If we can't get a quote from the API, pull one from the file
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader("src/main/resources/recentquotes.json"));
                Gson gson = new Gson();
                quotes = gson.fromJson(bufferedReader, Quote[].class);
                int randomIndex = (int) (Math.random() * quotes.length);
                return quotes[randomIndex].toString();

            } catch (FileNotFoundException e2) {
                System.out.println("File not found");
                e2.printStackTrace();
                //If we can't pull a quote from the file, return this winner
                return "Insanity: doing the same thing over and over again and expecting different results.";
            }
        }


    }


    @Override
    public String toString() {
        return String.format("%s" +
                "\n%s", this.text, this.author);
    }
}
