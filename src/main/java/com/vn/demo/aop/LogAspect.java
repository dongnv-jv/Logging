package com.vn.demo.aop;

import com.google.gson.Gson;
import com.vn.demo.entity.LogDetail;
import com.vn.demo.entity.LogEntity;
import com.vn.demo.helper.LogHelper;
import com.vn.demo.service.LogDetailService;
import com.vn.demo.service.LogService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Aspect
@Configuration
@RequiredArgsConstructor
public class LogAspect {
    private static final Logger log = LogManager.getLogger();
    private final LogHelper logHelper;
    private final LogService logService;
    private final LogDetailService logDetailService;
    private final Gson gson;


    @AfterReturning("@annotation(Log)")
    public void writeLogAfterReturn(JoinPoint joinPoint) throws Throwable {
        Map<String, List<Object>> listMap = logHelper.getDifferenceField();

        log.info("Get Map field success");
        LogEntity logEntity = LogEntity.builder()
                .idObject(logHelper.getId())
                .action(action(joinPoint))
                .function(function(joinPoint))
                .payLoad(getPayload(joinPoint))
                .createdDate(LocalDateTime.now())
                .build();
        LogEntity logEntitySave = logService.save(logEntity);
        Long id = logEntitySave.getId();
        log.info("Save Log success");

        for (Map.Entry<String, List<Object>> entry : listMap.entrySet()) {
            String key = entry.getKey();
            List<Object> values = entry.getValue();
            LogDetail logDetail = LogDetail.builder()
                    .field(key)
                    .logId(id)
                    .oldValue(getValue(values.get(0)))
                    .newValue(getValue(values.get(1)))
                    .createdDate(LocalDateTime.now())
                    .build();
            logDetailService.save(logDetail);
            log.info("Save Log-Detail success");
        }

        logHelper.clear();
    }

    private String getValue(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    private String action(JoinPoint joinPoint) throws Throwable {
        Method interfaceMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method implementationMethod = joinPoint.getTarget().getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
        String action = "";
        if (implementationMethod.isAnnotationPresent(Log.class)) {
            Log logAround = implementationMethod.getAnnotation(Log.class);
            action = logAround.action().name();
        }
        return action;
    }

    private String function(JoinPoint joinPoint) throws Throwable {
        Method interfaceMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method implementationMethod = joinPoint.getTarget().getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
        String function = "";
        if (implementationMethod.isAnnotationPresent(Log.class)) {
            Log logAround = implementationMethod.getAnnotation(Log.class);
            function = logAround.function().name();
        }
        return function;
    }


    private String getPayload(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            String arg = gson.toJson(joinPoint.getArgs()[i]);
            builder.append(arg);
        }
        return builder.toString();
    }

}

