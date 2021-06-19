package com.wesfalc.planning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
@RestController
public class Controller implements ErrorController {

    private double n = 12;  //numberOfTimesInterestIsCompoundedPerT ;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView indexPage(Model model) {
        log.info("Index page hit.");
        List<Event> events = new ArrayList<>();
        double P, PMT, ror, r;
        int t;
        P = 0;
        t = 30;
        PMT = 500;
        ror = 10;
        r = ror / 100;

        BasicData basicData = new BasicData((int) P, t, (int) PMT, ror);

        YearToEventMap yearToEventMap = new YearToEventMap();

        Result result = doTheMath(P, PMT, t, r, yearToEventMap);
        model.addAttribute("result", result);
        model.addAttribute("events", events);
        model.addAttribute("basicData", basicData);

        return new ModelAndView("mainpage");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/error", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView error(Model model) {
        log.info("There was an error");
        return indexPage(model);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/calculate", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView calculate(HttpServletRequest request,@RequestParam Map<String,String> allRequestParams,
                                  @RequestParam("startingAmount") double P,
                                  @RequestParam("monthlyContribution") double PMT,
                                  @RequestParam("years") int t,
                                  @RequestParam("annualRateOfReturn") double ror,
                                  Model model) {
        log.info("Calculating. startingAmount = {}, monthly contribution = {}, years = {}, rate of return = {}", P, PMT, t, ror);

        List<Event> events = new ArrayList<>();
        for (String param : allRequestParams.keySet()) {
            if(param.startsWith("eventData")) {
                String eventData = allRequestParams.get(param);
                Event event = Event.parse(eventData);
                log.info(event.toString());
                events.add(event);
            }
        }

        YearToEventMap yearToEventMap = new YearToEventMap();
        for (Event event : events) {
            yearToEventMap.putEvent(event.year(), event);
        }

        double r = ror / 100;

        BasicData basicData = new BasicData((int) P, t, (int) PMT, ror);

        Result result = doTheMath(P, PMT, t, r, yearToEventMap);
        model.addAttribute("result", result);
        model.addAttribute("events", events);
        model.addAttribute("basicData", basicData);

        return new ModelAndView("mainpage");
    }

    private Result doTheMath(double P, double PMT, int t, double r, YearToEventMap yearToEventMap) {

        Result result = new Result();
        result.startingAmount(P);
        result.rateOfReturn(r * 100);

        for (int time = 1; time <= t ; time ++) {

        List<Event> events = yearToEventMap.getEventsForYear(time);

        for (Event event : events) {
            log.info("added event to year {} , ev =  {}", time, event );
            ResultRow row = new ResultRow();
            row.event(event);

            switch (event.type()) {
               case ONE_TIME_CONTRIBUTION: P = P + event.amount(); break;
               case ONE_TIME_WITHDRAWAL:   P = P - event.amount(); break;
               case MONTHLY_CONTRIBUTION:  PMT = event.amount(); break;
               case MONTHLY_WITHDRAWAL:    PMT = -1 * event.amount(); break;
            }

            row.balance(P);
            result.addRow(row);
        }

            double gain = PMT * ((Math.pow((1 + r / n), n * 1) - 1) / (r / n));
            double lastP = P;
            P =  P * Math.pow((1 + r / n), n * 1) + gain;

            double earnings = P - lastP - 12 * PMT;

            ResultRow row = new ResultRow();
            row.year(time);
            row.investment(12 * PMT);
            row.earnings(earnings);
            row.balance(P);
            result.addRow(row);
        }

        return result;
    }

}
