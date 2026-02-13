package br.com.weg.application.dto.Partida;

import java.time.LocalDateTime;

public record PartidaRequestDTO (int idClubeA,
                                 int idClubeB,
                                 LocalDateTime dataHoraPartida,
                                 String localizacao){
}
