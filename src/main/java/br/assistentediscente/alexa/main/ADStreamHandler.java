package br.assistentediscente.alexa.main;

import br.assistentediscente.alexa.handlers.*;
import br.assistentediscente.alexa.handlers.custom.HandlerFactory;
import br.assistentediscente.alexa.handlers.custom.impl.*;
import br.assistentediscente.alexa.interceptors.LogRequestInterceptor;
import br.assistentediscente.alexa.interceptors.LogResponseInterceptor;
import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class ADStreamHandler extends SkillServlet {

    public ADStreamHandler(@Value("${skillid}") String skillId, HandlerFactory handlerFactory) {
        super(getSkill(skillId, handlerFactory));
    }

    private static Skill getSkill(String skillId, HandlerFactory handlerFactory) {
        return Skills.standard()
                .addRequestHandlers(
                        new LaunchRequestHandler(),
                        new FallBackIntentHandler(),
                        new CancelAndStopIntentHandler(),
                        new SessionEndedRequestHandler(),
                        handlerFactory.createGenericServicesIntentHandler(),
                        handlerFactory.createHelpIntentHandler(),
                        handlerFactory.createGradeByDisciplineNameIntentHandler(),
                        handlerFactory.createScheduleByDayIntentHandler(),
                        handlerFactory.createScheduleByDisciplineNameIntentHandler(),
                        handlerFactory.createGradeBySemesterIntentHandler(),
                        handlerFactory.createOverallAverageIntentHandler(),
                        handlerFactory.createTotalAbsencesByDisciplineNameIntentHandler()
                )
                .addRequestInterceptors(
                        new LogRequestInterceptor()
                )
                .addResponseInterceptors(
                        new LogResponseInterceptor()
                )
                .withSkillId(skillId)
                .build();
    }
}