package com.franchise.domain.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.franchise.domain.entity.Franchise;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.port.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceImplTest {

  @Mock
  private FranchiseRepository franchiseRepository;

  @InjectMocks
  private FranchiseServiceImpl franchiseService;

  private final Franchise franchise = Franchise.builder()
      .id(1)
      .name("Cosechas")
      .status("ACTIVE")
      .build();

  @Test
  void findByIdReturnsFranchiseWhenExists() {
    when(franchiseRepository.findById(1)).thenReturn(Mono.just(franchise));

    StepVerifier.create(franchiseService.findById(1))
        .expectNext(franchise)
        .verifyComplete();
  }

  @Test
  void findByIdFailsWithNotFoundWhenMissing() {
    when(franchiseRepository.findById(99)).thenReturn(Mono.empty());

    StepVerifier.create(franchiseService.findById(99))
        .expectErrorSatisfies(error -> {
          assertThat(error).isInstanceOf(NotFoundException.class);
          assertThat(error).hasMessage("Franchise not found: 99");
        })
        .verify();
  }

  @Test
  void saveReturnsCreatedFranchise() {
    when(franchiseRepository.insert(any(Franchise.class))).thenReturn(Mono.just(franchise));

    StepVerifier.create(franchiseService.save(franchise))
        .expectNext(franchise)
        .verifyComplete();
  }

  @Test
  void updateFailsWithNotFoundWhenNoRowsUpdated() {
    when(franchiseRepository.update(any(Franchise.class))).thenReturn(Mono.empty());

    StepVerifier.create(franchiseService.update(franchise))
        .expectError(NotFoundException.class)
        .verify();
  }

  @Test
  void deleteByIdTogglesStatusWhenExists() {
    final Franchise inactiveFranchise = Franchise.builder()
        .id(1)
        .name("Cosechas")
        .status("INACTIVE")
        .build();

    when(franchiseRepository.findById(1)).thenReturn(Mono.just(franchise), Mono.just(inactiveFranchise));
    when(franchiseRepository.delete(1)).thenReturn(Mono.empty());

    StepVerifier.create(franchiseService.deleteById(1))
        .expectNext(inactiveFranchise)
        .verifyComplete();
  }

  @Test
  void deleteByIdFailsWithNotFoundWhenMissing() {
    when(franchiseRepository.findById(99)).thenReturn(Mono.empty());

    StepVerifier.create(franchiseService.deleteById(99))
        .expectErrorSatisfies(error -> {
          assertThat(error).isInstanceOf(NotFoundException.class);
          assertThat(error).hasMessage("Franchise not found: 99");
        })
        .verify();
    verify(franchiseRepository, never()).delete(any());
  }
}
