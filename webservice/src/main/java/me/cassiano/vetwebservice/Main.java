package me.cassiano.vetwebservice;

import me.cassiano.vettsel.VettselSimplex;
import me.cassiano.vettsel.interfaces.PartialSolution;
import me.cassiano.vetwebservice.model.ErrorResponse;
import me.cassiano.vetwebservice.model.ParsedRequest;
import me.cassiano.vetwebservice.model.SolutionResponse;
import spark.Spark;

public class Main {

    public static void main(String[] args) {

        Spark.get("/", (request, response) -> "hello!");

        Spark.post("/xp", (request, response) -> {

            String responseStr = null;

            try {

                ParsedRequest parsedRequest =
                        ParsedRequest.parse(request.body());

                PartialSolution partialSolution = VettselSimplex.get().run(
                        parsedRequest.getFunction(), parsedRequest.getRestrictions());

                responseStr = SolutionResponse.fromSolution(partialSolution).toJson();

                response.status(200);

            } catch (Throwable throwable) {
                response.status(400);
                responseStr = new ErrorResponse(throwable).toJson();
            }


            response.type("application/json");

            return responseStr;
        });
    }

}
