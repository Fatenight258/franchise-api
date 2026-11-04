package com.franchise.infrastructure.adapter;

import com.franchise.domain.entity.Branch;
import io.r2dbc.spi.Readable;
import java.util.Objects;

public class BranchAdapter {

  public static Branch adapt(final Readable row) {
    return Branch.builder()
        .id(Objects.requireNonNull(row.get(Branch.PROPERTY_ID, Integer.class)))
        .franchiseId(row.get(Branch.PROPERTY_FRANCHISE_ID, Integer.class))
        .franchiseName(row.get(Branch.PROPERTY_FRANCHISE_NAME, String.class))
        .name(row.get(Branch.PROPERTY_NAME, String.class))
        .status(row.get(Branch.PROPERTY_STATUS, String.class))
        .build();
  }
}
