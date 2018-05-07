
public interface Constants {
	public static final int PORT_NUMBER = 1111;
	public static final String SERVER_ADDRESS = "localhost";
	
	public static final String COORDINATE_SIGNAL = "COORDINATE";
	public static final String COORDINATE_SIGNAL_A = "COORDINATEA";
	public static final String COORDINATE_SIGNAL_B = "COORDINATEB";
	public static final String CONNECT_SIGNAL = "CONNECT";
	public static final String ACKNOWLEDGEMENT_SIGNAL = "CONNECTED";
	public static final String WORD_UPDATE_SIGNAL = "WORD";
	public static final String WORD_CORRECT_SIGNAL = "CORRECT";
	public static final String SCORE_LIST_SIGNAL = "SCORE";
	public static final String FINAL_SCORE_LIST_SIGNAL = "FINAL_SCORE";
	public static final String GOT_THE_WORD_SIGNAL = "NOTIFY_EVERYONE";
	public static final String START_SIGNAL = "GAME NA DADDY";
	public static final String WANTS_TO_START_SIGNAL = "WANTS_TO_START";
	public static final String PRE_ROUND_SIGNAL = "PRE_ROUND";
	public static final String ONGOING_ROUND_SIGNAL = "ONGOING_ROUND";
	public static final String ONGOING_STAGE_SIGNAL = "ONGOING_STAGE";
	public static final String TIME_SIGNAL = "TIME";
	public static final String STAGE_TIME_SIGNAL = "STAGE_TIME";
	public static final String ARTIST_SIGNAL = "ARTIST";
	public static final String START_GUESSING_SIGNAL = "START_GUESSING";
	public static final String SERVER_PREFIX = "**SERVER: ";

	public static final String SPACE = " ";
	public static final String NEW_LINE = "\n";
	public static final String EMPTY_STRING = "";
	public static final int SOCKET_TIME_OUT = 100;

	public static final int BYTE_MAX_SIZE = 256;
	public static final int WAITING_FOR_PLAYERS = 0;
	public static final int PRE_ROUND = 1;
	public static final int ONGOING_ROUND = 2;
	public static final int POST_ROUND = 3;
	public static final int END_GAME = 4;
	public static final int PRE_STAGE = 5;
	public static final int ONGOING_STAGE = 6;
	public static final int POST_STAGE = 7;
}
