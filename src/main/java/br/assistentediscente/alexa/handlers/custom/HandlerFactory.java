package br.assistentediscente.alexa.handlers.custom;

import br.assistentediscente.alexa.handlers.custom.impl.HelpIntentHandler;
import br.assistentediscente.alexa.handlers.custom.impl.*;
import br.assistentediscente.alexa.requests.ApiAssistenteDiscente;
import org.springframework.stereotype.Component;

@Component
public class HandlerFactory {

    private final ApiAssistenteDiscente apiAssistenteDiscente;

    public HandlerFactory(ApiAssistenteDiscente apiAssistenteDiscente) {
        this.apiAssistenteDiscente = apiAssistenteDiscente;
    }

    public GenericServicesIntentHandler createGenericServicesIntentHandler() {
        return new GenericServicesIntentHandler(this.apiAssistenteDiscente);
    }

    public HelpIntentHandler createHelpIntentHandler() {
        return new HelpIntentHandler(this.apiAssistenteDiscente);
    }

    public ScheduleByDayIntentHandler createScheduleByDayIntentHandler() {
        return new ScheduleByDayIntentHandler(this.apiAssistenteDiscente);
    }

    public ScheduleByDisciplineNameIntentHandler createScheduleByDisciplineNameIntentHandler() {
        return new ScheduleByDisciplineNameIntentHandler(this.apiAssistenteDiscente);
    }

    public GradeByDisciplineNameIntentHandler createGradeByDisciplineNameIntentHandler() {
        return new GradeByDisciplineNameIntentHandler(this.apiAssistenteDiscente);
    }

    public GradeBySemesterIntentHandler createGradeBySemesterIntentHandler() {
        return new GradeBySemesterIntentHandler(this.apiAssistenteDiscente);
    }

    public OverallAverageIntentHandler createOverallAverageIntentHandler() {
        return new OverallAverageIntentHandler(this.apiAssistenteDiscente);
    }

    public TotalAbsencesByDisciplineNameIntentHandler createTotalAbsencesByDisciplineNameIntentHandler() {
        return new TotalAbsencesByDisciplineNameIntentHandler(this.apiAssistenteDiscente);
    }
}