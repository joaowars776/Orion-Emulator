package com.orionemu.server.network.messages.protocol.headers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Events {

    public static final short GetExtendedProfileMessageEvent = 3369; // PRODUCTION-201610052203-260805057
    public static final short RequestGuideToolMessageEvent = 2054; // PRODUCTION-201702211601-24705679
    public static final short RequestGuideAssistanceMessageEvent = 3407; // PRODUCTION-201610052203-260805057
    public static final short GetSellablePetBreedsMessageEvent = 276; // PRODUCTION-201610052203-260805057
    public static final short ScrGetUserInfoMessageEvent = 733; // PRODUCTION-201610052203-260805057
    public static final short GuideHandleHelpRequestMessageEvent = 2147; // PRODUCTION-201610052203-260805057
    public static final short GetForumUserProfileMessageEvent = 3273; // PRODUCTION-201610052203-260805057
    public static final short InfoRetrieveMessageEvent = 881; // PRODUCTION-201610052203-260805057
    public static final short GetRoomEntryDataMessageEvent = 2552; // PRODUCTION-201610052203-260805057
    public static final short GoToHotelViewMessageEvent = 1547; // PRODUCTION-201610052203-260805057
    public static final short KickUserMessageEvent = 3118; // PRODUCTION-201610052203-260805057
    public static final short PurchaseFromCatalogMessageEvent = 2770; // PRODUCTION-201610052203-260805057
    public static final short PopularRoomsMessageEvent = 1995;
    public static final short LoadHeightmapMessageEvent = 3791;
    public static final short AddUserToRoomMessageEvent = 2552;
    public static final short AddUserToRoom2MessageEvent = 3474;
    public static final short ShoutMessageEvent = 2507;
    public static final short StartTypingMessageEvent = 3411;
    public static final short StopTypingMessageEvent = 274;
    public static final short TalkMessageEvent = 816;
    public static final short GetRoomSettingsMessageEvent = 3802; // PRODUCTION-201610052203-260805057
    public static final short SaveRoomDataMessageEvent = 2618;
    public static final short FollowRoomInfoMessageEvent = 2547;
    public static final short ChangeLooksMessageEvent = 3767;
    public static final short OpenInventoryMessageEvent = 1404;
    public static final short WardrobeMessageEvent = 3608;
    public static final short WearBadgeMessageEvent = 3204;
    public static final short SaveWardrobeMessageEvent = 1596;
    public static final short ChangeHomeRoomMessageEvent = 2922;
    public static final short ChangeUsernameCheckMessageEvent = 3061;
    public static final short UpdateChatStyleMessageEvent = 2784;
    public static final short BadgeInventoryMessageEvent = 889;

    public static final short GetRoomBannedUsersMessageEvent = 1741; // PRODUCTION-201610052203-260805057
    public static final short GetPetInventoryMessageEvent = 1074; // PRODUCTION-201610052203-260805057
    public static final short DropHandItemMessageEvent = 3887; // PRODUCTION-201610052203-260805057
    public static final short ReleaseTicketMessageEvent = 1510; // PRODUCTION-201610052203-260805057
    public static final short GetModeratorRoomInfoMessageEvent = 3425; // PRODUCTION-201610052203-260805057
    public static final short SaveWiredEffectConfigMessageEvent = 81; // PRODUCTION-201610052203-260805057
    public static final short RespectPetMessageEvent = 2516; // PRODUCTION-201610052203-260805057
    public static final short GenerateSecretKeyMessageEvent = 20; // PRODUCTION-201610052203-260805057
    public static final short GetModeratorTicketChatlogsMessageEvent = 942; // PRODUCTION-201610052203-260805057
    public static final short GetAchievementsMessageEvent = 1926; // PRODUCTION-201610052203-26080505
    public static final short SaveWiredTriggerConfigMessageEvent = 795; // PRODUCTION-201610052203-260805057
    public static final short AcceptGroupMembershipMessageEvent = 3655; // PRODUCTION-201610052203-260805057
    public static final short GetGroupFurniSettingsMessageEvent = 571; // PRODUCTION-201610052203-260805057
    public static final short TakeAdminRightsMessageEvent = 3765; // PRODUCTION-201610052203-260805057
    public static final short RemoveAllRightsMessageEvent = 330; // PRODUCTION-201610052203-260805057
    public static final short UpdateThreadMessageEvent = 2158; // PRODUCTION-201610052203-260805057
    public static final short ManageGroupMessageEvent = 91; // PRODUCTION-201610052203-260805057
    public static final short ModifyRoomFilterListMessageEvent = 2130; // PRODUCTION-201610052203-260805057
    public static final short SSOTicketMessageEvent = 496; // PRODUCTION-201610052203-260805057
    public static final short JoinGroupMessageEvent = 1357; // PRODUCTION-201610052203-260805057
    public static final short DeclineGroupMembershipMessageEvent = 2288; // PRODUCTION-201610052203-260805057
    public static final short RemoveMyRightsMessageEvent = 3432; // PRODUCTION-201610052203-260805057
    public static final short ApplyHorseEffectMessageEvent = 2322; // PRODUCTION-201610052203-260805057
    public static final short GetPetInformationMessageEvent = 2604; // PRODUCTION-201610052203-260805057
    public static final short GiveHandItemMessageEvent = 3261; // PRODUCTION-201610052203-260805057
    public static final short UpdateFigureDataMessageEvent = 1475; // PRODUCTION-201610052203-260805057
    public static final short RemoveGroupMemberMessageEvent = 2861; // PRODUCTION-201610052203-260805057
    public static final short EventLogMessageEvent = 2756; // PRODUCTION-201610052203-260805057
    public static final short RefreshCampaignMessageEvent = 2773; // PRODUCTION-201610052203-260805057
    public static final short GetPromotableRoomsMessageEvent = 624; // PRODUCTION-201610052203-260805057
    public static final short UseOneWayGateMessageEvent = 1749; // PRODUCTION-201610052203-260805057
    public static final short AddStickyNoteMessageEvent = 2851; // PRODUCTION-201610052203-260805057
    public static final short GetSelectedBadgesMessageEvent = 2981; // PRODUCTION-201610052203-260805057
    public static final short UpdateStickyNoteMessageEvent = 500; // PRODUCTION-201610052203-260805057
    public static final short CloseTicketMesageEvent = 1692; // PRODUCTION-201610052203-260805057
    public static final short RequestBuddyMessageEvent = 2701; // PRODUCTION-201610052203-260805057
    public static final short GetFurnitureAliasesMessageEvent = 3474; // PRODUCTION-201610052203-260805057
    public static final short RequestFurniInventoryMessageEvent = 2591; // PRODUCTION-201610052203-260805057
    public static final short ModerationKickMessageEvent = 2178; // PRODUCTION-201610052203-260805057
    public static final short OpenFlatConnectionMessageEvent = 3010; // PRODUCTION-201610052203-260805057
    public static final short DanceMessageEvent = 3421; // PRODUCTION-201610052203-260805057
    public static final short RemoveBuddyMessageEvent = 3372; // PRODUCTION-201610052203-260805057
    public static final short LatencyTestMessageEvent = 3555; // PRODUCTION-201610052203-260805057
    public static final short YouTubeGetNextVideo = 1912; // PRODUCTION-201610052203-260805057
    public static final short SetObjectDataMessageEvent = 2952; // PRODUCTION-201610052203-260805057
    public static final short MessengerInitMessageEvent = 1853; // PRODUCTION-201610052203-260805057
    public static final short PickUpBotMessageEvent = 3624; // PRODUCTION-201610052203-260805057
    public static final short ActionMessageEvent = 2924; // PRODUCTION-201610052203-260805057
    public static final short LookToMessageEvent = 1562; // PRODUCTION-201610052203-260805057
    public static final short ToggleMoodlightMessageEvent = 1609; // PRODUCTION-201610052203-260805057
    public static final short FollowFriendMessageEvent = 3250; // PRODUCTION-201610052203-260805057
    public static final short PickUpPetMessageEvent = 3961; // PRODUCTION-201610052203-260805057
    public static final short GetForumsListDataMessageEvent = 2671; // PRODUCTION-201610052203-260805057
    public static final short IgnoreUserMessageEvent = 2367; // PRODUCTION-201610052203-260805057
    public static final short DeleteRoomMessageEvent = 1242; // PRODUCTION-201610052203-260805057
    public static final short StartQuestMessageEvent = 3618; // PRODUCTION-201610052203-260805057
    public static final short GetGiftWrappingConfigurationMessageEvent = 661; // PRODUCTION-201610052203-260805057
    public static final short UpdateGroupIdentityMessageEvent = 1041; // PRODUCTION-201610052203-260805057
    public static final short RideHorseMessageEvent = 1410; // PRODUCTION-201610052203-260805057
    public static final short FindRandomFriendingRoomMessageEvent = 3610; // PRODUCTION-201610052203-260805057
    public static final short GetModeratorUserChatlogMessageEvent = 1989; // PRODUCTION-201610052203-260805057
    public static final short GetWardrobeMessageEvent = 2870; // PRODUCTION-201610052203-260805057
    public static final short MuteUserMessageEvent = 1565; // PRODUCTION-201610052203-260805057
    public static final short UpdateForumSettingsMessageEvent = 2260; // PRODUCTION-201610052203-260805057
    public static final short ApplyDecorationMessageEvent = 32; // PRODUCTION-201610052203-260805057
    public static final short GetBotInventoryMessageEvent = 2195; // PRODUCTION-201610052203-260805057
    public static final short UseHabboWheelMessageEvent = 2971; // PRODUCTION-201610052203-260805057
    public static final short EditRoomPromotionMessageEvent = 63; // PRODUCTION-201610052203-260805057
    public static final short GetModeratorUserInfoMessageEvent = 3083; // PRODUCTION-201610052203-260805057
    public static final short PlaceBotMessageEvent = 2107; // PRODUCTION-201610052203-260805057
    public static final short GetCatalogPageMessageEvent = 711; // PRODUCTION-201610052203-260805057
    public static final short GetThreadsListDataMessageEvent = 3240; // PRODUCTION-201610052203-260805057
    public static final short DiceOffMessageEvent = 2314; // PRODUCTION-201610052203-260805057
    public static final short LetUserInMessageEvent = 3278; // PRODUCTION-201610052203-260805057
    public static final short SetActivatedBadgesMessageEvent = 3345; // PRODUCTION-201610052203-260805057
    public static final short UpdateGroupSettingsMessageEvent = 1806; // PRODUCTION-201610052203-260805057
    public static final short ApproveNameMessageEvent = 3114; // PRODUCTION-201610052203-260805057
    public static final short SubmitNewTicketMessageEvent = 2207; // PRODUCTION-201610052203-260805057
    public static final short DeleteGroupMessageEvent = 2045; // PRODUCTION-201610052203-260805057
    public static final short DeleteStickyNoteMessageEvent = 1202; // PRODUCTION-201610052203-260805057
    public static final short GetGroupInfoMessageEvent = 2748; // PRODUCTION-201610052203-260805057
    public static final short GetStickyNoteMessageEvent = 2921; // PRODUCTION-201610052203-260805057
    public static final short DeclineBuddyMessageEvent = 2285; // PRODUCTION-201610052203-260805057
    public static final short OpenGiftMessageEvent = 1771; // PRODUCTION-201610052203-260805057
    public static final short GiveRoomScoreMessageEvent = 812; // PRODUCTION-201610052203-260805057
    public static final short SetGroupFavouriteMessageEvent = 3081; // PRODUCTION-201610052203-260805057
    public static final short SetMannequinNameMessageEvent = 740; // PRODUCTION-201610052203-260805057
    public static final short RoomDimmerSavePresetMessageEvent = 1918; // PRODUCTION-201610052203-260805057
    public static final short UpdateGroupBadgeMessageEvent = 53; // PRODUCTION-201610052203-260805057
    public static final short PickTicketMessageEvent = 2788; // PRODUCTION-201610052203-260805057
    public static final short SetTonerMessageEvent = 2303; // PRODUCTION-201610052203-260805057
    public static final short RespectUserMessageEvent = 1001; // PRODUCTION-201610052203-260805057
    public static final short DeleteGroupThreadMessageEvent = 3450; // PRODUCTION-201610052203-260805057
    public static final short DeleteGroupReplyMessageEvent = 1419; // PRODUCTION-201610052203-260805057
    public static final short RateRoomMessageEvent = 1662;

    public static final short CreditFurniRedeemMessageEvent = 3440; // PRODUCTION-201610052203-260805057
    public static final short ModerationMsgMessageEvent = 3683; // PRODUCTION-201610052203-260805057
    public static final short ToggleYouTubeVideoMessageEvent = 3139; // PRODUCTION-201610052203-260805057
    public static final short UpdateNavigatorSettingsMessageEvent = 3307; // PRODUCTION-201610052203-260805057
    public static final short ToggleMuteToolMessageEvent = 862; // PRODUCTION-201610052203-260805057
    public static final short ChatMessageEvent = 2141; // PRODUCTION-201610052203-260805057
    public static final short SaveRoomSettingsMessageEvent = 3522; // PRODUCTION-201610052203-260805057
    public static final short PurchaseFromCatalogAsGiftMessageEvent = 298; // PRODUCTION-201610052203-260805057
    public static final short GetGroupCreationWindowMessageEvent = 2706; // PRODUCTION-201610052203-260805057
    public static final short GiveAdminRightsMessageEvent = 2636; // PRODUCTION-201610052203-260805057
    public static final short GetGroupMembersMessageEvent = 439; // PRODUCTION-201610052203-260805057
    public static final short ModerateRoomMessageEvent = 2372; // PRODUCTION-201610052203-260805057
    public static final short GetForumStatsMessageEvent = 3200; // PRODUCTION-201610052203-260805057
    public static final short GetPromoArticlesMessageEvent = 2142; // Klar
    public static final short SitMessageEvent = 2877; // PRODUCTION-201610052203-260805057
    public static final short SetSoundSettingsMessageEvent = 2625; // PRODUCTION-201610052203-260805057
    public static final short ModerationCautionMessageEvent = 612; // PRODUCTION-201610052203-260805057
    public static final short InitializeFloorPlanSessionMessageEvent = 332; // PRODUCTION-201610052203-260805057
    public static final short ModeratorActionMessageEvent = 3487; // PRODUCTION-201610052203-260805057
    public static final short PostGroupContentMessageEvent = 3907; // PRODUCTION-201610052203-260805057
    public static final short GetModeratorRoomChatlogMessageEvent = 1987; // PRODUCTION-201610052203-260805057
    public static final short GetUserFlatCatsMessageEvent = 2068; // PRODUCTION-201610052203-260805057
    public static final short RemoveRightsMessageEvent = 3432; // PRODUCTION-201610052203-260805057
    public static final short ModerationBanMessageEvent = 1324; // PRODUCTION-201610052203-260805057
    public static final short CanCreateRoomMessageEvent = 2569; // Klar
    public static final short UseWallItemMessageEvent = 2480; // PRODUCTION-201610052203-260805057
    public static final short PlaceObjectMessageEvent = 1205; // PRODUCTION-201610052203-260805057
    public static final short OpenBotActionMessageEvent = 868; // PRODUCTION-201610052203-260805057
    public static final short GetEventCategoriesMessageEvent = 671; // PRODUCTION-201610052203-260805057
    public static final short MoveWallItemMessageEvent = 1293; // PRODUCTION-201610052203-260805057
    public static final short UpdateGroupColoursMessageEvent = 2522; // PRODUCTION-201610052203-260805057
    public static final short HabboSearchMessageEvent = 658; // PRODUCTION-201610052203-260805057
    public static final short CommandBotMessageEvent = 2742; // PRODUCTION-201610052203-260805057
    public static final short SetCustomStackingHeightMessageEvent = 3556; // PRODUCTION-201610052203-260805057
    public static final short UnIgnoreUserMessageEvent = 670; // PRODUCTION-201610052203-260805057
    public static final short GetGuestRoomMessageEvent = 1327; // PRODUCTION-201610052203-260805057
    public static final short SetMannequinFigureMessageEvent = 328; // PRODUCTION-201610052203-260805057
    public static final short AssignRightsMessageEvent = 2729; // PRODUCTION-201610052203-260805057
    public static final short GetYouTubeTelevisionMessageEvent = 869; // PRODUCTION-201610052203-260805057
    public static final short SetMessengerInviteStatusMessageEvent = 1930; // PRODUCTION-201610052203-260805057
    public static final short UpdateFloorPropertiesMessageEvent = 3121; // PRODUCTION-201610052203-260805057
    public static final short GetMoodlightConfigMessageEvent = 3841; // PRODUCTION-201610052203-260805057
    public static final short PurchaseRoomPromotionMessageEvent = 2205; // PRODUCTION-201610052203-260805057
    public static final short SendRoomInviteMessageEvent = 436; // PRODUCTION-201610052203-260805057
    public static final short ModerationMuteMessageEvent = 1273; // PRODUCTION-201610052203-260805057
    public static final short SetRelationshipMessageEvent = 1574; // PRODUCTION-201610052203-260805057
    public static final short ChangeMottoMessageEvent = 3273; // PRODUCTION-201610052203-260805057
    public static final short UnbanUserFromRoomMessageEvent = 3538; // PRODUCTION-201610052203-260805057
    public static final short GetRoomRightsMessageEvent = 3448; // PRODUCTION-201610052203-260805057
    public static final short PurchaseGroupMessageEvent = 3291; // PRODUCTION-201610052203-260805057
    public static final short CreateFlatMessageEvent = 3560; // PRODUCTION-201610052203-260805057
    public static final short CreateNewRoomMessageEvent = 481;
    public static final short FeaturedRoomsMessageEvent = 2791;
    public static final short AddToStaffPickedRoomsMessageEvent = 2341;
    public static final short PromotedRoomsMessageEvent = 1372;

    public static final short InitializeSexualHelpToolEvent = 3198;
    public static final short InitializeBullyingHelpToolEvent = 3188;

    public static final short ThrowDiceMessageEvent = 2690; // PRODUCTION-201610052203-260805057
    public static final short SaveWiredConditionConfigMessageEvent = 1226; // PRODUCTION-201610052203-260805057
    public static final short GetCatalogOfferMessageEvent = 422; // PRODUCTION-201610052203-260805057
    public static final short PickupObjectMessageEvent = 3594; // PRODUCTION-201610052203-260805057
    public static final short CancelQuestMessageEvent = 1483; // PRODUCTION-201610052203-260805057
    public static final short NavigatorSearchMessageEvent = 462; // PRODUCTION-201610052203-260805057
    public static final short MoveAvatarMessageEvent = 1660; // PRODUCTION-201610052203-260805057
    public static final short GetClientVersionMessageEvent = 4000; // PRODUCTION-201610052203-260805057
    public static final short InitializeNavigatorMessageEvent = 3956; // PRODUCTION-201610052203-260805057
    public static final short OwnRoomsMessageEvent = 3428;
    public static final short GetRoomFilterListMessageEvent = 2751; // PRODUCTION-201610052203-260805057
    public static final short WhisperMessageEvent = 2677; // PRODUCTION-201610052203-260805057
    public static final short InitCryptoMessageEvent = 1182; // PRODUCTION-201610052203-260805057
    public static final short GetPetTrainingPanelMessageEvent = 1819; // PRODUCTION-201610052203-260805057
    public static final short MoveObjectMessageEvent = 680; // PRODUCTION-201610052203-260805057
    public static final short SendMsgMessageEvent = 143; // PRODUCTION-201610052203-260805057
    public static final short CancelTypingMessageEvent = 2778; // PRODUCTION-201610052203-260805057
    public static final short GetGroupFurniConfigMessageEvent = 7263; // PRODUCTION-201610052203-260805057
    public static final short SearchRoomMessageEvent = 3366;
    public static final short LoadSearchRoomMessageEvent = 3663;
    public static final short WisperMessageEvent = 678;
    public static final short WalkMessageEvent = 1015;
    public static final short ApplyActionMessageEvent = 548;
    public static final short ApplyDanceMessageEvent = 2488;
    public static final short ApplySignMessageEvent = 2156;


    public static final short RemoveGroupFavouriteMessageEvent = 337; // PRODUCTION-201610052203-260805057
    public static final short PlacePetMessageEvent = 202; // PRODUCTION-201610052203-260805057
    public static final short ModifyWhoCanRideHorseMessageEvent = 3319; // PRODUCTION-201610052203-260805057
    public static final short GetRelationshipsMessageEvent = 3230; // PRODUCTION-201610052203-260805057
    public static final short GetCatalogIndexMessageEvent = 3324; // PRODUCTION-201610052203-260805057
    public static final short ConfirmLoveLockMessageEvent = 3813; // PRODUCTION-201610052203-260805057
    public static final short RemoveSaddleFromHorseMessageEvent = 1999; // PRODUCTION-201610052203-260805057
    public static final short AcceptBuddyMessageEvent = 3011; // PRODUCTION-201610052203-260805057
    public static final short GetQuestListMessageEvent = 251; // PRODUCTION-201610052203-260805057
    public static final short SaveWardrobeOutfitMessageEvent = 3605; // PRODUCTION-201610052203-260805057
    public static final short BanUserMessageEvent = 1753; // PRODUCTION-201610052203-260805057
    public static final short GetThreadDataMessageEvent = 911; // PRODUCTION-201610052203-260805057
    public static final short GetBadgesMessageEvent = 1214; // PRODUCTION-201610052203-260805057
    public static final short UseFurnitureMessageEvent = 945; // PRODUCTION-201610052203-260805057
    public static final short GoToFlatMessageEvent = 3938; // PRODUCTION-201610052203-260805057
    public static final short GetModeratorUserRoomVisitsMessageEvent = 483; // PRODUCTION-201610052203-260805057
    public static final short GetSanctionStatusMessageEvent = 1804; // PRODUCTION-201610052203-260805057
    public static final short SetChatPreferenceMessageEvent = 2805; // PRODUCTION-201610052203-260805057
    public static final short ResizeNavigatorMessageEvent = 2806; // PRODUCTION-201610052203-260805057
    public static final short SongInventoryMessageEvent = 2500; // PRODUCTION-201610052203-260805057
    public static final short SongIdMessageEvent = 2516; // PRODUCTION-201610052203-260805057
    public static final short SongDataMessageEvent = 3947; // PRODUCTION-201610052203-260805057
    public static final short PlaylistMessageEvent = 3939; // PRODUCTION-201610052203-260805057
    public static final short PlaylistAddMessageEvent = 676; // PRODUCTION-201610052203-260805057
    public static final short PlaylistRemoveMessageEvent = 2190; // PRODUCTION-201610052203-260805057
    public static final short StaffPickRoomMessageEvent = 2512; // PRODUCTION-201610052203-260805057
    public static final short SubmitPollAnswerMessageEvent = 442; // PRODUCTION-201610052203-260805057
    public static final short GetPollMessageEvent = 237; // PRODUCTION-201610052203-260805057
    public static final short UpdateSnapshotsMessageEvent = 1773; // PRODUCTION-201610052203-260805057
    public static final short InitTradeMessageEvent = 585; // PRODUCTION-201610052203-260805057
    public static final short TradingOfferItemMessageEvent = 970; // PRODUCTION-201610052203-260805057
    public static final short TradingOfferItemsMessageEvent = 781; // PRODUCTION-201610052203-260805057
    public static final short TradingRemoveItemMessageEvent = 226; // PRODUCTION-201610052203-260805057
    public static final short TradingAcceptMessageEvent = 2526; // PRODUCTION-201610052203-260805057
    public static final short TradingCancelMessageEvent = 1208; // PRODUCTION-201610052203-260805057
    public static final short TradingModifyMessageEvent = 555; // PRODUCTION-201610052203-260805057
    public static final short TradingConfirmMessageEvent = 1987; // PRODUCTION-201610052203-260805057
    public static final short TradingCancelConfirmMessageEvent = 1054; // PRODUCTION-201610052203-260805057
    public static final short RedeemVoucherMessageEvent = 3265; // PRODUCTION-201610052203-260805057
    public static final short ChangeNameMessageEvent = 412; // PRODUCTION-201610052203-260805057
    public static final short CheckValidNameMessageEvent = 2902; // PRODUCTION-201610052203-260805057
    public static final short SaveNavigatorSearchMessageEvent = 3253; // PRODUCTION-201610052203-260805057
    public static final short DeleteNavigatorSavedSearchMessageEvent = 1743; // PRODUCTION-201610052203-260805057
    public static final short SaveFootballGateMessageEvent = 1989; // PRODUCTION-201610052203-260805057
    public static final short UserBadgesMessageEvent = 1956;
    public static final short SaveFloorMessageEvent = 2200;

    public static final short NuxInstructionMessageEvent = 195;
    public static final short GetTalentTrackMessageEvent = 3271;

    //Klara
    public static final short GameCenterRequestGamesEvent = 166;
    public static final short InitializeGamecenterEvent = 3426;
    public static final short GameCenterJoinGameEvent = 3918;
    public static final short GetPlayableGamesMessageEvent = 3071;

    //Snowstorm
    public static final short CheckGameDirectoryStatusParser = 379;
    public static final short LoadStageReadyParser = 2962;
    public static final short SetUserMoveTargetParser = 1096;
    public static final short ReuquestFullStatusUpdateParser = 2872;
    public static final short ThrowSnowballAtHumanParser = 3736;
    public static final short ThrowSnowballAtPositionParser = 2047;
    public static final short MakeSnowballParser = 1243;
    public static final short GameChatParser = 2247;
    public static final short ExitGameParser = 309;
    public static final short LeaveGameParser = 929;


    private static Map<Short, String> eventPacketNames = new HashMap<>();

    static {
        try {
            for (Field field : Events.class.getDeclaredFields()) {
                if (!Modifier.isPrivate(field.getModifiers()))
                    eventPacketNames.put(field.getShort(field.getName()), field.getName());
            }
        } catch (Exception ignored) {

        }
    }

    public static String valueOfId(short packetId) {
        if (eventPacketNames.containsKey(packetId)) {
            return eventPacketNames.get(packetId);
        }

        return "UnknownMessageEvent";
    }
}