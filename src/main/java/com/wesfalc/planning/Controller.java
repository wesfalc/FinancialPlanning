package com.wesfalc.planning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;

@Slf4j
@SpringBootApplication
@RestController
public class Controller {

    private double n  = 12;  //numberOfTimesInterestIsCompoundedPerT ;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/calculate", method = {RequestMethod.POST, RequestMethod.GET})
    public String calculate(HttpServletRequest request,
                            @RequestParam("startingAmount") double P,
                            @RequestParam("monthlyContribution") double PMT,
                            @RequestParam("years") double t,
                            @RequestParam("annualRateOfReturn") double ror) {
        log.info("Calculating.");

        double r = ror / 100;

        String result = doTheMath(P, PMT, t, r);

        return result;
    }

    private String doTheMath(double P, double PMT, double t, double r) {


        StringBuilder output = new StringBuilder();
        String str = String.format("Starting Amount = %s <br>Hypothetical Annual Rate of Return = %.1f%%<br>", moneyFormat(P), r * 100);

        output.append(str);

        for (int time = 1; time <= t ; time ++) {
            double gain = PMT * ((Math.pow((1 + r / n), n * 1) - 1) / (r / n));
            double lastP = P;
            P =  P * Math.pow((1 + r / n), n * 1) + gain;

            double earnings = P - lastP - 12 * PMT;

            str = String.format("Year = %2d Investment = %s Earnings = %s Balance = %s<br>" ,
                    time, moneyFormat(12 * PMT), moneyFormat(earnings), moneyFormat(P));

            output.append(str);
            output.append("\n");
        }
        return output.toString().replaceAll("\\s", "&nbsp;");
    }

    private static String moneyFormat(double finalAmount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(finalAmount);
        return moneyString;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sampleCalc", method = {RequestMethod.POST, RequestMethod.GET})
    public String sampleCalc(HttpServletRequest request) throws Exception {
        log.info("Sample calcualtion.");

        return "Sample calculation.";
    }
}
