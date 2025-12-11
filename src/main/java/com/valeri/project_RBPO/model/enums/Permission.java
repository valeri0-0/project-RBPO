package com.valeri.project_RBPO.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission
{
    READ("read"),
    MODIFICATION("modify");

    private final String permission;
}