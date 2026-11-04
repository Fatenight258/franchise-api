package com.franchise.application.mapper;

import com.franchise.application.model.BranchCreateDto;
import com.franchise.application.model.BranchDto;
import com.franchise.application.model.Status;
import com.franchise.domain.entity.Branch;

public class BranchMapper {

  public static BranchDto toDto(final Branch branch) {
    return new BranchDto()
        .id(branch.getId())
        .franchiseId(branch.getFranchiseId())
        .franchiseName(branch.getFranchiseName())
        .name(branch.getName())
        .status(Status.fromValue(branch.getStatus()));
  }

  public static Branch toCreateEntity(final Integer franchiseId, final BranchCreateDto branchCreateDto) {
    return Branch.builder()
        .franchiseId(franchiseId)
        .name(branchCreateDto.getName())
        .build();
  }

  public static Branch toUpdateEntity(final Integer id, final BranchCreateDto branchCreateDto) {
    return Branch.builder()
        .id(id)
        .name(branchCreateDto.getName())
        .build();
  }
}
