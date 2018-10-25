package br.ufrgs.foodbook.dto.group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestData
{
    String memberName, groupName, creatorName;
}
