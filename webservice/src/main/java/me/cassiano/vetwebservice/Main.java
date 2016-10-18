package me.cassiano.vetwebservice;

import me.cassiano.vettsel.VettselSimplex;
import me.cassiano.vettsel.interfaces.PartialSolution;
import me.cassiano.vetwebservice.model.ParsedRequest;
import me.cassiano.vetwebservice.model.SolutionResponse;
import spark.Spark;

public class Main {

    public static void main(String[] args) {

        Spark.get("/", (request, response) -> "hello!");

        Spark.post("/xp", (request, response) -> {

            ParsedRequest parsedRequest =
                    ParsedRequest.parse(request.body());

            PartialSolution partialSolution = VettselSimplex.get().run(
                    parsedRequest.getFunction(), parsedRequest.getRestrictions());

            response.status(200);
            response.type("application/json");

            return SolutionResponse.fromSolution(partialSolution).toJson();
        });
    }

}
