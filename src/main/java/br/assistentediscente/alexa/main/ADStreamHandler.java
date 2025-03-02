package br.assistentediscente.alexa.main;

import br.assistentediscente.alexa.handlers.*;
import br.assistentediscente.alexa.handlers.custom.impl.*;
import br.assistentediscente.alexa.interceptors.LogRequestInterceptor;
import br.assistentediscente.alexa.interceptors.LogResponseInterceptor;
import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ADStreamHandler extends SkillServlet {

    @Value("${skillid}")
    private static String skillId;

    private static Skill getSkill() {

        return Skills.standard()
                .addRequestHandlers(
                        new LaunchRequestHandler(),
                        new HelpIntentHandler(),
                        new FallBackIntentHandler(),
                        new CancelAndStopIntentHandler(),
                        new SessionEndedRequestHandler(),
                        new ScheduleByDayIntentHandler(),
                        new ScheduleByDisciplineNameIntentHandler(),
                        new GradeByDisciplineNameIntentHandler(),
                        new GradeBySemesterIntentHandler(),
                        new OverallAverageIntentHandler(),
                        new TotalAbsencesByDisciplineNameIntentHandler(),
                        new GenericServicesIntentHandler()
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

    public ADStreamHandler() {
        super(getSkill());
    }
}