package com.orionemu.server.network.messages.protocol.headers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Composers {

    public static final short InitCryptoMessageComposer = 3016;
    public static final short SecretKeyMessageComposer = 847;
    public static final short ActionMessageComposer = 2439;
    public static final short AdvancedAlertMessageComposer = 2664;
    public static final short CanCreateRoomMessageComposer = 3353;
    public static final short DanceMessageComposer = 3030;
    public static final short ShoutMessageComposer = 1192;
    public static final short TalkMessageComposer = 3372;
    public static final short UpdateFloorItemMessageComposer = 3169;
    public static final short UpdateInventoryMessageComposer = 345;
    public static final short BotInventoryMessageComposer = 823;
    public static final short WardrobeMessageComposer = 3709;
    public static final short SlideObjectBundleMessageComposer = 2473;
    public static final short PetInventoryMessageComposer = 1074;
    public static final short PetInformationMessageComposer = 2307;
    public static final short PromoArticlesMessageComposer = 3897;
    public static final short PlaylistMessageComposer = 3523;
    public static final short RoomRatingMessageComposer = 3533;
    public static final short AlertMessageComposer = 656;
    public static final short WiredRewardMessageComposer = 934; // PRODUCTION-201610052203-260805057
    public static final short HeightMapMessageComposer = 2749; // PRODUCTION-201610052203-260805057
    public static final short CallForHelpPendingCallsMessageComposer = 1442;
    public static final short GroupMembersMessageComposer = 128; // PRODUCTION-201610052203-260805057
    public static final short ManageGroupMessageComposer = 3351; // PRODUCTION-201610052203-260805057
    public static final short FloodControlMessageComposer = 3728; // PRODUCTION-201610052203-260805057
    public static final short FlatControllerAddedMessageComposer = 3763; // PRODUCTION-201610052203-260805057
    public static final short UniqueMachineIDMessageComposer = 339; // KLAR
    public static final short ChatMessageComposer = 3372;
    public static final short NavigatorSettingsMessageComposer = 2174; // KLAR
    public static final short MOTDNotificationMessageComposer = 2291; // KLAR
    public final static short GameCenterAccountInfoComposer = 1109; //KLAR
    public static final short FlatCreatedMessageComposer = 3379; // PRODUCTION-201610052203-260805057
    public static final short ScrSendUserInfoMessageComposer = 2179; // PRODUCTION-201610052203-260805057
    public static final short CheckPetNameMessageComposer = 1674; // PRODUCTION-201610052203-260805057
    public static final short QuestAbortedMessageComposer = 1172; // PRODUCTION-201610052203-260805057
    public static final short RespectPetNotificationMessageComposer = 1301; // PRODUCTION-201610052203-260805057
    public static final short CampaignMessageComposer = 360; // Klar
    public static final short SendHotelViewLooksMessageComposer = 3690; // PRODUCTION-201610052203-260805057
    public static final short LatencyResponseMessageComposer = 884; // Kan vara fel, värt att söka upp senare
    public static final short FriendListUpdateMessageComposer = 161; // PRODUCTION-201610052203-260805057
    public static final short ObjectAddMessageComposer = 1013; // PRODUCTION-201610052203-260805057
    public static final short RoomRightsListMessageComposer = 2547; // PRODUCTION-201610052203-260805057
    public static final short NewGroupInfoMessageComposer = 2684; // PRODUCTION-201610052203-260805057
    public static final short RoomForwardMessageComposer = 3168; // PRODUCTION-201610052203-260805057
    public static final short GroupFurniSettingsMessageComposer = 2491; // PRODUCTION-201610052203-260805057
    public static final short BroadcastMessageAlertMessageComposer = 1567; // PRODUCTION-201610052203-260805057
    public static final short CatalogIndexMessageComposer = 1319; // PRODUCTION-201610052203-260805057
    public static final short ProfileInformationMessageComposer = 3563; // PRODUCTION-201610052203-260805057
    public static final short CreditBalanceMessageComposer = 2507; // PRODUCTION-201610052203-260805057
    public static final short CatalogUpdatedMessageComposer = 1652; // PRODUCTION-201610052203-260805057
    public static final short UserTypingMessageComposer = 1860; // PRODUCTION-201610052203-260805057
    public static final short ObjectRemoveMessageComposer = 3386; // PRODUCTION-201610052203-260805057
    public static final short RoomEntryInfoMessageComposer = 2318; // PRODUCTION-201610052203-260805057
    public static final short CatalogOfferMessageComposer = 133; // PRODUCTION-201610052203-260805057
    public static final short HabboUserBadgesMessageComposer = 2059; // PRODUCTION-201610052203-260805057
    public static final short FlatAccessibleMessageComposer = 106; // PRODUCTION-201610052203-260805057
    public static final short FloorPlanSendDoorMessageComposer = 3768; // PRODUCTION-201610052203-260805057
    public static final short SleepMessageComposer = 908; // PRODUCTION-201610052203-260805057
    public static final short FlatControllerRemovedMessageComposer = 3094; // PRODUCTION-201610052203-260805057
    public static final short UserObjectMessageComposer = 2895; // TODO
    public static final short MessengerInitMessageComposer = 3332; // PRODUCTION-201610052203-260805057
    public static final short AchievementScoreMessageComposer = 1942; // PRODUCTION-201610052203-260805057
    public static final short PerkAllowancesMessageComposer = 3899; // PRODUCTION-201610052203-260805057
    public static final short ActivityPointsMessageComposer = 2446; // PRODUCTION-201610052203-260805057
    public static final short BadgeDefinitionsMessageComposer = 1994; // PRODUCTION-201610052203-260805057
    public static final short UserRightsMessageComposer = 1723; // PRODUCTION-201610052203-260805057
    public static final short FavouritesMessageComposer = 47; // PRODUCTION-201610052203-260805057
    public static final short AvailabilityStatusMessageComposer = 2389; // Kan va fel
    public static final short CatalogPageMessageComposer = 1732; // PRODUCTION-201610052203-260805057
    public static final short NavigatorFlatListMessageComposer = 2694;
    public static final short PurchaseOKMessageComposer = 2408; // PRODUCTION-201610052203-260805057
    public static final short PopularRoomTagsResultMessageComposer = 2484; // PRODUCTION-201610052203-260805057
    public static final short TalentTrackMessageComposer = 3775;
    public static final short RoomSettingsDataMessageComposer = 329; // PRODUCTION-201610052203-260805057
    public static final short CloseConnectionMessageComposer = 2919; // ezzz
    public static final short RoomVisualizationSettingsMessageComposer = 3323; // PRODUCTION-201610052203-260805057
    public static final short RoomSettingsSavedMessageComposer = 1858; // PRODUCTION-201610052203-260805057
    public static final short RoomReadyMessageComposer = 1667; // PRODUCTION-201610052203-260805057
    public static final short RoomPropertyMessageComposer = 3642; // PRODUCTION-201610052203-260805057
    public static final short UsersMessageComposer = 411; // PRODUCTION-201610052203-260805057
    public static final short ObjectsMessageComposer = 3670; // PRODUCTION-201610052203-260805057
    public static final short ItemsMessageComposer = 1563; // PRODUCTION-201610052203-260805057
    public static final short FloorHeightMapMessageComposer = 3768; // PRODUCTION-201610052203-260805057
    public static final short UserNameChangeMessageComposer = 962; // PRODUCTION-201610052203-260805057
    public static final short UpdateUsernameMessageComposer = 2853; // PRODUCTION-201610052203-260805057
    public static final short CarryObjectMessageComposer = 2258; // PRODUCTION-201610052203-260805057
    public static final short UserUpdateMessageComposer = 3977; // PRODUCTION-201610052203-260805057
    public static final short UpdateStackMapMessageComposer = 3265; // PRODUCTION-201610052203-260805057
    public static final short SellablePetBreedsMessageComposer = 1261; // PRODUCTION-201610052203-260805057
    public static final short OpenBotActionMessageComposer = 3945; // PRODUCTION-201610052203-260805057
    public static final short UserChangeMessageComposer = 1859; // PRODUCTION-201610052203-260805057
    public static final short HabboActivityPointNotificationMessageComposer = 882; // PRODUCTION-201610052203-260805057
    public static final short FeaturedRoomsMessageComposer = 2540;
    public static final short WiredEffectConfigMessageComposer = 1800;
    public static final short HideWiredConfigMessageComposer = 1115; // PRODUCTION-201610052203-260805057
    public static final short WiredTriggerConfigMessageComposer = 2550; // PRODUCTION-201610052203-260805057
    public static final short WiredConditionConfigMessageComposer = 1476; // PRODUCTION-201610052203-260805057

    public static final short TradingClosedMessageComposer = 7; // TODO
    public static final short PromotableRoomsMessageComposer = 781; // TODO
    public static final short NavigatorCollapsedCategoriesMessageComposer = 2333; // TODO
    public static final short TradingUpdateMessageComposer = 2331; // Todo
    public static final short ThreadsListDataMessageComposer = 266; // TODO
    public static final short GroupFurniConfigMessageComposer = 2025; // TODO
    public static final short ModeratorInitMessageComposer = 3933; // TODO

    public static final short ItemAddMessageComposer = 1926; // PRODUCTION-201610052203-26080505
    public static final short GroupForumDataMessageComposer = 3879; // PRODUCTION-201610052203-260805057
    public static final short ItemUpdateMessageComposer = 1640; // PRODUCTION-201610052203-260805057
    public static final short AchievementsMessageComposer = 1858; // PRODUCTION-201610052203-260805057
    public static final short BuddyListMessageComposer = 0; // PRODUCTION-201610052203-260805057
    public static final short YoutubeDisplayPlaylistsMessageComposer = 1157; // PRODUCTION-201610052203-260805057
    public static final short TradingCompleteMessageComposer = 648; // PRODUCTION-201610052203-260805057
    public static final short ModeratorRoomChatlogMessageComposer = 150; // PRODUCTION-201610052203-260805057
    public static final short GroupInfoMessageComposer = 3487; // PRODUCTION-201610052203-260805057
    public static final short FurniListRemoveMessageComposer = 2759; // PRODUCTION-201610052203-260805057
    public static final short FurniListNotificationMessageComposer = 469; // PRODUCTION-201610052203-260805057
    public static final short RoomInfoUpdatedMessageComposer = 2674; // PRODUCTION-201610052203-260805057
    public static final short AvatarEffectMessageComposer = 445; // PRODUCTION-201610052203-260805057
    public static final short OpenConnectionMessageComposer = 3929; // PRODUCTION-201610052203-260805057
    public static final short FurniListMessageComposer = 1293; // PRODUCTION-201610052203-260805057
    public static final short PostUpdatedMessageComposer = 1565; // PRODUCTION-201610052203-260805057
    public static final short UserFlatCatsMessageComposer = 1535; // PRODUCTION-201610052203-260805057
    public static final short ObjectUpdateMessageComposer = 3169; // PRODUCTION-201610052203-260805057
    public static final short ThreadUpdatedMessageComposer = 3837; // PRODUCTION-201610052203-260805057
    public static final short HabboSearchResultMessageComposer = 171; // PRODUCTION-201610052203-260805057
    public static final short RespectNotificationMessageComposer = 3836; // PRODUCTION-201610052203-260805057
    public static final short PetHorseFigureInformationMessageComposer = 3014; // PRODUCTION-201610052203-260805057
    public static final short ModeratorUserInfoMessageComposer = 1826; // PRODUCTION-201610052203-260805057
    public static final short YouAreControllerMessageComposer = 2423; // PRODUCTION-201610052203-260805057
    public static final short RefreshFavouriteGroupMessageComposer = 1507; // PRODUCTION-201610052203-260805057
    public static final short AchievementUnlockedMessageComposer = 176; // PRODUCTION-201610052203-260805057
    public static final short FlatAccessDeniedMessageComposer = 256; // PRODUCTION-201610052203-260805057
    public static final short NavigatorFlatCatsMessageComposer = 462; // PRODUCTION-201610052203-260805057
    public static final short TradingStartMessageComposer = 2078; // PRODUCTION-201610052203-260805057
    public static final short NewBuddyRequestMessageComposer = 1546; // PRODUCTION-201610052203-260805057
    public static final short DoorbellMessageComposer = 384; // PRODUCTION-201610052203-260805057
    public static final short OpenGiftMessageComposer = 3191; // PRODUCTION-201610052203-260805057
    public static final short CantConnectMessageComposer = 3053; // PRODUCTION-201610052203-260805057
    public static final short BuildersClubMembershipMessageComposer = 257; // PRODUCTION-201610052203-260805057
    public static final short PetTrainingPanelMessageComposer = 620; // PRODUCTION-201610052203-260805057
    public static final short QuestCompletedMessageComposer = 2730; // PRODUCTION-201610052203-260805057
    public static final short ForumsListDataMessageComposer = 359; // PRODUCTION-201610052203-260805057
    public static final short ModeratorUserChatlogMessageComposer = 1212; // PRODUCTION-201610052203-260805057
    public static final short GiftWrappingConfigurationMessageComposer = 2504; // PRODUCTION-201610052203-260805057
    public static final short FloorPlanFloorMapMessageComposer = 2956; // PRODUCTION-201610052203-260805057
    public static final short ThreadReplyMessageComposer = 2954; // PRODUCTION-201610052203-260805057
    public static final short GroupCreationWindowMessageComposer = 3696; // PRODUCTION-201610052203-260805057
    public static final short GetGuestRoomResultMessageComposer = 495; // PRODUCTION-201610052203-260805057
    public static final short RoomNotificationMessageComposer = 2664; // PRODUCTION-201610052203-260805057
    public static final short SoundSettingsMessageComposer = 2440; // PRODUCTION-201610052203-260805057
    public static final short BadgeEditorPartsMessageComposer = 2081; // PRODUCTION-201610052203-260805057
    public static final short NewConsoleMessageMessageComposer = 2109; // PRODUCTION-201610052203-260805057
    public static final short AddExperiencePointsMessageComposer = 479; // PRODUCTION-201610052203-260805057
    public static final short AvatarEffectsMessageComposer = 628; // PRODUCTION-201610052203-260805057
    public static final short QuestListMessageComposer = 2121; // PRODUCTION-201610052203-260805057
    public static final short UnbanUserFromRoomMessageComposer = 2569; // PRODUCTION-201610052203-260805057
    public static final short StickyNoteMessageComposer = 1393; // PRODUCTION-201610052203-260805057
    public static final short SanctionStatusMessageComposer = 685; // PRODUCTION-201610052203-260805057
    public static final short MaintenanceStatusMessageComposer = 1015; // PRODUCTION-201610052203-260805057
    public static final short BuddyRequestsMessageComposer = 1666; // PRODUCTION-201610052203-260805057
    public static final short AuthenticationOKMessageComposer = 531; // PRODUCTION-201610052203-260805057
    public static final short QuestStartedMessageComposer = 2367; // PRODUCTION-201610052203-260805057
    public static final short RoomEventMessageComposer = 0x0300; // PRODUCTION-201610052203-260805057
    public static final short RoomMuteSettingsMessageComposer = 1819; // PRODUCTION-201610052203-260805057
    public static final short ModeratorSupportTicketResponseMessageComposer = 3173; // PRODUCTION-201610052203-260805057
    public static final short YouTubeDisplayVideoMessageComposer = 2508; // PRODUCTION-201610052203-260805057
    public static final short ModeratorSupportTicketMessageComposer = 1555; // PRODUCTION-201610052203-260805057
    public static final short RoomInviteMessageComposer = 258; // PRODUCTION-201610052203-260805057
    public static final short FurniListUpdateMessageComposer = 345; // PRODUCTION-201610052203-260805057
    public static final short BadgesMessageComposer = 3866; // PRODUCTION-201610052203-260805057
    public static final short NavigatorSearchResultSetMessageComposer = 2149; // PRODUCTION-201610052203-260805057
    public static final short IgnoreStatusMessageComposer = 758; // PRODUCTION-201610052203-260805057
    public static final short MoodlightConfigMessageComposer = 3496; // PRODUCTION-201610052203-260805057
    public static final short FurnitureAliasesMessageComposer = 1467; // PRODUCTION-201610052203-260805057
    public static final short LoveLockDialogueCloseMessageComposer = 1364; // PRODUCTION-201610052203-260805057
    public static final short TradingErrorMessageComposer = 3129; // PRODUCTION-201610052203-260805057
    public static final short ModeratorRoomInfoMessageComposer = 1904; // PRODUCTION-201610052203-260805057
    public static final short LoveLockDialogueMessageComposer = 2001; // PRODUCTION-201610052203-260805057
    public static final short PurchaseErrorMessageComposer = 451; // PRODUCTION-201610052203-260805057
    public static final short GiftWrappingErrorMessageComposer = 3984; // PRODUCTION-201610052203-260805057
    public static final short WhisperMessageComposer = 1123; // PRODUCTION-201610052203-260805057
    public static final short CatalogItemDiscountMessageComposer = 3535; // PRODUCTION-201610052203-260805057
    public static final short HabboGroupBadgesMessageComposer = 1077; // PRODUCTION-201610052203-260805057
    public static final short ThreadDataMessageComposer = 1618; // PRODUCTION-201610052203-260805057
    public static final short TradingFinishMessageComposer = 3056; // PRODUCTION-201610052203-260805057
    public static final short GenericErrorMessageComposer = 1530; // PRODUCTION-201610052203-260805057
    public static final short NavigatorPreferencesMessageComposer = 2445; // PRODUCTION-201610052203-260805057
    public static final short MutedMessageComposer = 1048; // PRODUCTION-201610052203-260805057
    public static final short YouAreOwnerMessageComposer = 1001; // PRODUCTION-201610052203-260805057
    public static final short ModeratorTicketChatlogMessageComposer = 833; // PRODUCTION-201610052203-260805057
    public static final short UserRemoveMessageComposer = 3666; // PRODUCTION-201610052203-260805057
    public static final short ModeratorUserRoomVisitsMessageComposer = 558; // PRODUCTION-201610052203-260805057
    public static final short RoomErrorNotifMessageComposer = 1802; // PRODUCTION-201610052203-260805057
    public static final short NavigatorLiftedRoomsMessageComposer = 1063; // PRODUCTION-201610052203-260805057
    public static final short NavigatorMetaDataParserMessageComposer = 1713; // PRODUCTION-201610052203-260805057
    public static final short GetRelationshipsMessageComposer = 3754; // PRODUCTION-201610052203-260805057
    public static final short ItemRemoveMessageComposer = 2003; // PRODUCTION-201610052203-260805057
    public static final short ThreadCreatedMessageComposer = 520; // PRODUCTION-201610052203-260805057
    public static final short EnforceCategoryUpdateMessageComposer = 2704; // PRODUCTION-201610052203-260805057
    public static final short AchievementProgressedMessageComposer = 2267; // PRODUCTION-201610052203-260805057
    public static final short GetRoomBannedUsersMessageComposer = 1072; // PRODUCTION-201610052203-260805057
    public static final short LoveLockDialogueSetLockedMessageComposer = 1364; // PRODUCTION-201610052203-260805057
    public static final short TradingAcceptMessageComposer = 2457; // PRODUCTION-201610052203-260805057
    public static final short SongInventoryMessageComposer = 2526; // PRODUCTION-201610052203-260805057
    public static final short SongIdMessageComposer = 582; // PRODUCTION-201610052203-260805057
    public static final short SongDataMessageComposer = 173; // klar
    public static final short PlayMusicMessageComposer = 3087; // PRODUCTION-201610052203-260805057
    public static final short QuickPollMessageComposer = 3446; // PRODUCTION-201610052203-260805057
    public static final short QuickPollResultMessageComposer = 3661; // PRODUCTION-201610052203-260805057

    public static final short InitializePollMessageComposer = 832; // PRODUCTION-201610052203-260805057
    public static final short PollMessageComposer = 3428; // PRODUCTION-201610052203-260805057
    public static final short AvatarAspectUpdateMessageComposer = 2259; // PRODUCTION-201610052203-260805057
    public static final short YouAreSpectatorMessageComposer = 1281; // PRODUCTION-201610052203-260805057

    public static final short NameChangeUpdateMessageComposer = 2276; // PRODUCTION-201610052203-260805057
    public static final short GuideSessionAttachedMessageComposer = 3796; // PRODUCTION-201610052203-260805057
    public static final short GuideSessionErrorMessageComposer = 3489; // PRODUCTION-201610052203-260805057
    public static final short GuideToolsMessageComposer = 175; // PRODUCTION-201610052203-260805057
    public static final short NavigatorSavedSearchesMessageComposer = 255; // PRODUCTION-201702211601-24705679
    public final static short GameCenterGameListComposer = 2299;
    public final static short GameButtonStatusComposer = 3805;
    public final static short GameCenterAchievementsConfigurationComposer = 3032;


    public final static short BaseJumpUnloadGameComposer = 1748;
    public final static short BaseJumpLoadGameComposer = 2098;
    public final static short BaseJumpLeaveQueueComposer = 2673;
    public final static short BaseJumpJoinQueueComposer = 3577;
    public final static short LeaveGameComposer = 1477; // okej denna composer, ska ta fram den för annan revision

    public final static short PlayableGamesMessageComposer = 1436;

    public final static short TalentTrackLevelMessageComposer = 1932;
    public final static short TalentLevelUpMessageComposer = 1752;
    public final static short PostQuizAnswersMessageComposer = 3420;
    public final static short QuizDataMessageComposer = 1669;
    public final static short QuizResultsMessageComposer = 3420;
    public final static short HotelWillCloseInMinutesAndBackComposer = 2642;
    public final static short HotelViewGoalComposer = 2951;


    private static Map<Short, String> composerPacketNames = new HashMap<>();

    static {
        try {
            for (Field field : Composers.class.getDeclaredFields()) {
                if (!Modifier.isPrivate(field.getModifiers()))
                    composerPacketNames.put(field.getShort(field.getName()), field.getName());
            }
        } catch (Exception ignored) {

        }
    }

    public static String valueOfId(short packetId) {
        if (composerPacketNames.containsKey(packetId)) {
            return composerPacketNames.get(packetId);
        }

        return "UnknownMessageComposer";
    }
}
