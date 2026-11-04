package com.franchise.application.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.franchise.application.model.BranchCreateDto;
import com.franchise.application.model.BranchDto;
import com.franchise.application.model.Status;
import com.franchise.domain.entity.Branch;
import com.franchise.domain.entity.Franchise;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.service.BranchService;
import com.franchise.domain.service.FranchiseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BranchApplicationServiceImplTest {

  @Mock
  private BranchService branchService;

  @Mock
  private FranchiseService franchiseService;

  @InjectMocks
  private BranchApplicationServiceImpl branchApplicationService;

  private final Franchise franchise = Franchise.builder()
      .id(1)
      .name("Cosechas")
      .status("ACTIVE")
      .build();

  private final Branch branch = Branch.builder()
      .id(8)
      .franchiseId(1)
      .franchiseName("Cosechas")
      .name("Envigado")
      .status("ACTIVE")
      .build();

  @Test
  void saveBranchCreatesBranchForExistingFranchise() {
    when(franchiseService.findById(1)).thenReturn(Mono.just(franchise));
    when(branchService.save(any(Branch.class))).thenReturn(Mono.just(branch));

    StepVerifier.create(branchApplicationService.saveBranch(1, new BranchCreateDto("Envigado")))
        .assertNext(branchDto -> {
          assertThat(branchDto.getId()).isEqualTo(8);
          assertThat(branchDto.getFranchiseId()).isEqualTo(1);
          assertThat(branchDto.getFranchiseName()).isEqualTo("Cosechas");
          assertThat(branchDto.getName()).isEqualTo("Envigado");
          assertThat(branchDto.getStatus()).isEqualTo(Status.ACTIVE);
        })
        .verifyComplete();

    final ArgumentCaptor<Branch> captor = ArgumentCaptor.forClass(Branch.class);
    verify(branchService).save(captor.capture());
    assertThat(captor.getValue().getFranchiseId()).isEqualTo(1);
    assertThat(captor.getValue().getName()).isEqualTo("Envigado");
  }

  @Test
  void saveBranchFailsWhenFranchiseDoesNotExist() {
    when(franchiseService.findById(99)).thenReturn(Mono.error(NotFoundException.franchise(99)));

    StepVerifier.create(branchApplicationService.saveBranch(99, new BranchCreateDto("Envigado")))
        .expectError(NotFoundException.class)
        .verify();

    verify(branchService, never()).save(any());
  }

  @Test
  void updateBranchRenamesExistingBranch() {
    final Branch renamed = Branch.builder()
        .id(8).franchiseId(1).franchiseName("Cosechas").name("Envigado Centro").status("ACTIVE")
        .build();
    when(branchService.findById(8)).thenReturn(Mono.just(branch));
    when(branchService.update(any(Branch.class))).thenReturn(Mono.just(renamed));

    StepVerifier.create(branchApplicationService.updateBranch(8, new BranchCreateDto("Envigado Centro")))
        .assertNext(branchDto -> assertThat(branchDto.getName()).isEqualTo("Envigado Centro"))
        .verifyComplete();
  }

  @Test
  void updateBranchFailsWhenBranchDoesNotExist() {
    when(branchService.findById(99)).thenReturn(Mono.error(NotFoundException.branch(99)));

    StepVerifier.create(branchApplicationService.updateBranch(99, new BranchCreateDto("X")))
        .expectError(NotFoundException.class)
        .verify();

    verify(branchService, never()).update(any());
  }
}
