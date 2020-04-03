package populacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CalculoIbge {

    @Inject
    Logger logger;

    @Inject
    @RestClient
    IbgeService ibgeService;

    public Long calcular(Long datahora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            ProjecaoIbge projecaoIbge = ibgeService.populacao();
            Long populacao = projecaoIbge.getProjecao().getPopulacao();
            PeriodoMedio periodoMedio = projecaoIbge.getProjecao().getPeriodoMedio();
            Long incrementoPopulacional = periodoMedio.getIncrementoPopulacional();
            Long obito = periodoMedio.getObito();
            Long nascimento = periodoMedio.getNascimento();
            Long projecao = populacao;
            
            ZoneId zoneId =  ZoneId.systemDefault();
            LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(datahora*1000), zoneId);
            Date parse = format.parse(projecaoIbge.getHorario());
            LocalDateTime dateProjecao = parse.toInstant().atZone(zoneId).toLocalDateTime();

            Long periodo = Long.valueOf(dateProjecao.until(date, ChronoUnit.MILLIS));

            projecao += periodo / incrementoPopulacional;
            projecao += periodo / nascimento;
            projecao -= periodo / obito;

            StringBuilder builder = new StringBuilder();
            builder.append(" datahora chamada: "+ date.format(formatter));
            builder.append(" datahora projecao: "+ projecaoIbge.getHorario());
            builder.append(" população estimada: "+ populacao);
            builder.append(" população projetaca para data: "+ projecao);
            logger.write(builder.toString());
            
            return projecao;
        } catch (ParseException e) {
            e.printStackTrace();
        } 
        return 0L;
    }
}