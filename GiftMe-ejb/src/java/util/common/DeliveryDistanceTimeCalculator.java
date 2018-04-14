/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.common;

import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Farhan Angullia
 */
//Uses Google DistanceMatrix API
public class DeliveryDistanceTimeCalculator {

    private static final String API_KEY = ***REMOVED***;

    //private static long[][] matrix;
    public static long getDriveDist(String addrOne, String addrTwo) throws ApiException, InterruptedException, IOException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.DRIVING)
                .avoid(RouteRestriction.TOLLS)
                .language("en-US")
                .await();

        long distApart = result.rows[0].elements[0].distance.inMeters;

        return distApart;
    }

    public static String getArrivalTime(String addrOne, String addrTwo) throws ApiException, InterruptedException, IOException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.DRIVING)
                .avoid(RouteRestriction.TOLLS)
                .language("en-US")
                .await();

        //long distApart = result.rows[0].elements[0].distance.inMeters;
        String time = result.rows[0].elements[0].duration.humanReadable;
       System.out.println(result.destinationAddresses[0]);
   System.out.println(result.originAddresses[0]);
        return time;
    }

    
    
    
     public static List<String> getExactAddresses(String addrOne, String addrTwo) throws ApiException, InterruptedException, IOException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.DRIVING)
                .avoid(RouteRestriction.TOLLS)
                .language("en-US")
                .await();

        List<String> addresses = new ArrayList<>();
           addresses.add(result.originAddresses[0]);
        addresses.add(result.destinationAddresses[0]);
        addresses.add(result.originAddresses[0]);

        return addresses;
    }
    
    
    
    
}
