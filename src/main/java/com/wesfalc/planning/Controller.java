package com.wesfalc.planning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
@RestController
public class Controller {

    private double n  = 12;  //numberOfTimesInterestIsCompoundedPerT ;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/calculate", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView calculate(HttpServletRequest request,
                                  @RequestParam("startingAmount") double P,
                                  @RequestParam("monthlyContribution") double PMT,
                                  @RequestParam("years") double t,
                                  @RequestParam("annualRateOfReturn") double ror,
                                  Model model) {
        log.info("Calculating. startingAmount = {}, monthly contribution = {}, years = {}, rate of return = {}", P, PMT, t, ror);

        double r = ror / 100;

        Result result = doTheMath(P, PMT, t, r);
        model.addAttribute("result", result);

        return new ModelAndView("results");
    }

    private Result doTheMath(double P, double PMT, double t, double r) {

        Result result = new Result();
        result.startingAmount(P);
        result.rateOfReturn(r * 100);

        ResultRow row = new ResultRow();

        for (int time = 1; time <= t ; time ++) {
            double gain = PMT * ((Math.pow((1 + r / n), n * 1) - 1) / (r / n));
            double lastP = P;
            P =  P * Math.pow((1 + r / n), n * 1) + gain;

            double earnings = P - lastP - 12 * PMT;

            row.year(time);
            row.investment(12 * PMT);
            row.earnings(earnings);
            row.balance(P);
            result.addRow(row);

            row = new ResultRow();



        }

        return result;
    }



    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sampleCalc", method = {RequestMethod.POST, RequestMethod.GET})
    public String sampleCalc(HttpServletRequest request) throws Exception {
        log.info("Sample calcualtion.");

        return "Sample calculation.";
    }
}
