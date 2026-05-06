package com.jwhi.som.domains.evt

enum class EvtOpIds(val value: UShort) {
    DISPLAY_MESSAGE(0u),
    DISPLAY_FORMATTED_MESSAGE(1u),
    SHOP_OPEN(23u),
    WARP_NPC(25u),
    WARP_ENEMY(26u),
    BEGIN_SCREEN_EFFECT(40u),
    END_SCREEN_EFFECT(41u),
    WARP_PLAYER_DETAILED(60u),
    WARP_PLAYER(61u),
    CHANGE_PLAYER_PARAMETER(80u),
    SET_PLAYER_PARAMETER_IN_COUNTER(84u),
    SAVE_POINT(121u),
    IF_COUNTER_CONDITION(140u),
    IF_MESSAGE_PROMPT(141u),
    OTHERWISE(142u),
    END_IF(143u),
    CHANGE_COUNTER(144u),
    CHANGE_PAGE(145u),
    END(UShort.MAX_VALUE),
}

/*
UnimplementedOperation(opId=40, opSize=8, bytes=[40, 0, 8, 0, 5, 0, 0, 0])
39 = {UnimplementedOperation@5190} UnimplementedOperation(opId=41, opSize=4, bytes=[41, 0, 4, 0])
40 = {UnimplementedOperation@5191} UnimplementedOperation(opId=149, opSize=8, bytes=[-107, 0, 8, 0, 1, 0, 0, 0])
44 = {UnimplementedOperation@5195} UnimplementedOperation(opId=40, opSize=8, bytes=[40, 0, 8, 0, 4, 0, 0, 0])
45 = {ChangePlayerParameter@5196} ChangePlayerParameter(opId=80, opSize=12, playerParameter=HP, wayChanged=DECREMENT_BY, itemId=0, unimplementedBytes=0, value=5, bytes=[80, 0, 12, 0, 0, 2, 0, 0, 0, 0, 5, 0])
46 = {UnimplementedOperation@5197} UnimplementedOperation(opId=41, opSize=4, bytes=[41, 0, 4, 0])
52 = {UnimplementedOperation@5203} UnimplementedOperation(opId=40, opSize=8, bytes=[40, 0, 8, 0, 4, 1, 0, 0])
53 = {ChangePlayerParameter@5204} ChangePlayerParameter(opId=80, opSize=12, playerParameter=HP, wayChanged=DECREMENT_BY, itemId=0, unimplementedBytes=0, value=25, bytes=[80, 0, 12, 0, 0, 2, 0, 0, 0, 0, 25, 0])
54 = {UnimplementedOperation@5205} UnimplementedOperation(opId=41, opSize=4, bytes=[41, 0, 4, 0])
55 = {EndIf@5206} EndIf(opId=143, opSize=4, bytes=[])
59 = {IfCounterCondition@5210} IfCounterCondition(opId=140, opSize=12, counterId=4, value=6, valueIsCounterId=false, compareType=LESS_THAN, bytes=[-116, 0, 12, 0, 4, 0, 6, 0, 0, 3, 0, 0])
60 = {UnimplementedOperation@5211} UnimplementedOperation(opId=61, opSize=24, bytes=[61, 0, 24, 0, 21, 97, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
61 = {DisplayFormattedMessage@5212} DisplayFormattedMessage(opId=1, opSize=60, textColorRed=0, textColorGreen=193, textColorBlue=62, textColorExtraByte=0, fontWeight=0, fontWeightExtraBytes=0, text=Be Careful. You wre close to \r\ndeath., fontName=MS Mincho, bytes=[1, 0, 60, 0, 0, -63, 62, 0, 0, 0, 0, 0, 66, 101, 32, 67, 97, 114, 101, 102, 117, 108, 46, 32, 89, 111, 117, 32, 119, 114, 101, 32, 99, 108, 111, 115, 101, 32, 116, 111, 32, 13, 10, 100, 101, 97, 116, 104, 46, 0, 77, 83, 32, 77, 105, 110, 99, 104, 111, 0])
62 = {EndIf@5213} EndIf(opId=143, opSize=4, bytes=[])
64 = {UnimplementedOperation@5215} UnimplementedOperation(opId=150, opSize=8, bytes=[-106, 0, 8, 0, 1, 0, 10, 0])
65 = {IfCounterCondition@5216} IfCounterCondition(opId=140, opSize=12, counterId=10, value=60, valueIsCounterId=false, compareType=GREATER_THAN, bytes=[-116, 0, 12, 0, 10, 0, 60, 0, 0, 2, 0, 0])
66 = {UnimplementedOperation@5217} UnimplementedOperation(opId=20, opSize=8, bytes=[20, 0, 8, 0, 2, 0, 0, 0])
73 = {UnimplementedOperation@5224} UnimplementedOperation(opId=20, opSize=8, bytes=[20, 0, 8, 0, 2, 0, 0, 0])
75 = {UnimplementedOperation@5226} UnimplementedOperation(opId=21, opSize=8, bytes=[21, 0, 8, 0, 2, 0, 0, 0])
77 = {UnimplementedOperation@5228} UnimplementedOperation(opId=22, opSize=8, bytes=[22, 0, 8, 0, 4, 0, 0, 0])
79 = {ShopOpen@5230} ShopOpen(opId=23, opSize=8, shopId=5, additionalBytes=0, bytes=[23, 0, 8, 0, 5, 0, 0, 0])
81 = {UnimplementedOperation@5232} UnimplementedOperation(opId=25, opSize=24, bytes=[25, 0, 24, 0, 2, 0, 6, 69, 120, 0, 0, 0, -51, -52, -52, 61, -51, -52, 76, 62, 0, 0, 0, 63])
83 = {UnimplementedOperation@5234} UnimplementedOperation(opId=26, opSize=24, bytes=[26, 0, 24, 0, 2, 0, 7, 69, 14, 1, 0, 0, -51, -52, -52, -67, -51, -52, 76, -66, 0, 0, 0, -65])
85 = {UnimplementedOperation@5236} UnimplementedOperation(opId=27, opSize=8, bytes=[27, 0, 8, 0, 2, 0, 0, 0])
87 = {UnimplementedOperation@5238} UnimplementedOperation(opId=28, opSize=8, bytes=[28, 0, 8, 0, 2, 0, 0, 0])

evtEvents[39].definition.name + "\n${evtEvents[39].pageOperations.values.first().first()}"

Terminate Enemy (2 Ooze)
UnimplementedOperation(opId=28, opSize=8, bytes=[28, 0, 8, 0, 2, 0, 0, 0])
29 Terminate NPC (2 Bandit)
UnimplementedOperation(opId=27, opSize=8, bytes=[27, 0, 8, 0, 2, 0, 0, 0])
25 Activate Item (0004 FirDagger)
UnimplementedOperation(opId=22, opSize=8, bytes=[22, 0, 8, 0, 4, 0, 0, 0])
24 Activate Enemy (0002 Ooze)
UnimplementedOperation(opId=21, opSize=8, bytes=[21, 0, 8, 0, 2, 0, 0, 0])
23 Activate NPC (Bandit)
UnimplementedOperation(opId=20, opSize=8, bytes=[20, 0, 8, 0, 2, 0, 0, 0])



DISPLAY BMP
UnimplementedOperation(opId=42, opSize=32, bytes=[42, 0, 32, 0, 0, 5, 0, 0, 69, 120, 97, 109, 112, 108, 101, 66, 77, 80, 54, 52, 48, 120, 52, 56, 53, 46, 98, 109, 112, 0, 0, 0])

 */
