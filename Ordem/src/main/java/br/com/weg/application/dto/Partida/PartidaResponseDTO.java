package br.com.weg.application.dto.Partida;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PartidaResponseDTO (int id,
                                  int idClubeA,
                                  int idClubeB,
                                  LocalDate dataHoraPartida,
                                  String localizacao) {
}
