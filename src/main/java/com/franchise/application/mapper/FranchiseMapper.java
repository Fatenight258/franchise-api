package com.franchise.application.mapper;

import com.franchise.application.model.FranchiseCreateDto;
import com.franchise.application.model.FranchiseDto;
import com.franchise.application.model.Status;
import com.franchise.domain.entity.Franchise;

public class FranchiseMapper {

  public static FranchiseDto toDto(final Franchise franchise) {
    return new FranchiseDto()
        .id(franchise.getId())
        .name(franchise.getName())
        .status(Status.fromValue(franchise.getStatus()));
  }

  public static Franchise toCreateEntity(final FranchiseCreateDto franchiseCreateDto) {
    return Franchise.builder()
        .name(franchiseCreateDto.getName())
        .build();
  }

  public static Franchise toUpdateEntity(final Integer id, final FranchiseCreateDto franchiseCreateDto) {
    return Franchise.builder()
        .id(id)
        .name(franchiseCreateDto.getName())
        .build();
  }
}
