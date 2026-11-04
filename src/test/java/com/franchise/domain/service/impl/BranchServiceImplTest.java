package com.franchise.domain.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.franchise.domain.entity.Branch;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.port.BranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

  @Mock
  private BranchRepository branchRepository;

  @InjectMocks
  private BranchServiceImpl branchService;

  private final Branch branch = Branch.builder()
      .id(1)
      .franchiseId(1)
      .franchiseName("Cosechas")
      .name("El Poblado")
      .status("ACTIVE")
      .build();

  @Test
  void findByIdReturnsBranchWhenExists() {
    when(branchRepository.findById(1)).thenReturn(Mono.just(branch));

    StepVerifier.create(branchService.findById(1))
        .expectNext(branch)
        .verifyComplete();
  }

  @Test
  void findByIdFailsWithNotFoundWhenMissing() {
    when(branchRepository.findById(99)).thenReturn(Mono.empty());

    StepVerifier.create(branchService.findById(99))
        .expectErrorSatisfies(error -> {
          assertThat(error).isInstanceOf(NotFoundException.class);
          assertThat(error).hasMessage("Branch not found: 99");
        })
        .verify();
  }

  @Test
  void saveReturnsCreatedBranch() {
    when(branchRepository.insert(any(Branch.class))).thenReturn(Mono.just(branch));

    StepVerifier.create(branchService.save(branch))
        .expectNext(branch)
        .verifyComplete();
  }

  @Test
  void updateFailsWithNotFoundWhenNoRowsUpdated() {
    when(branchRepository.update(any(Branch.class))).thenReturn(Mono.empty());

    StepVerifier.create(branchService.update(branch))
        .expectError(NotFoundException.class)
        .verify();
  }
}
