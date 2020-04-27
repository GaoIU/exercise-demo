package com.gaoc.common.constant;

public class BaseConstant {

	/** 请求头，用户ID */
	public static final String USER_ID = "X-User-Id";

	/** 加密私钥 */
	public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI6W+V/UoJIR0F9hEqRoe/rUc7lb1n+jsa/WtTaIxmliFdFxknhyPsFGt1EOzs50jhHe1OtS0tXKUuuBvU4M/Qs4+qbI2ZiHdVUxvNMGhSLcwNCRnjCxiUh4WHIPZpyUXTwQpmamxia7gSo2fyzUZ1+h83FbQY2sUEYW8ipLen9XAgMBAAECgYAaiqG6o9KLvgMibehRcB170CYGX8Dqm2bMy1OJ5iXAogBbrx9LikCCK46d6Pq31VxImUja0Nzr7LBIpCq/p41GSzUrelivr6jqT0VORtbSB1j4s7TuumdGaeg7u6gSPo2DMVNYtioT/0Zff4vwn5l3gOSgXM2yrvrlnoytmffn4QJBAPR3v+fi7DdJ5348X2NUoYOZL07EjwunPYUA/VRBKIaBpfiwIgkfCX21DHU0DO/7ChuXwWm1fmze1EgU+6KhbjECQQCVUOviKAJIZHefkuLmC/OcHnVTGbYkwpxP5PJUnbjqNXNog2oges+ROisE+6Drc40GAljt6lYfPU3vTRKmzTwHAkBmDiO4CzxvbFPOmCd+EROIG4frSxUoWT3Oa3ZWGQlw9WizI9xhkGpm5xD3UK5h9JvonvaoSZMoofFe0humbdvRAkBRbsaWmrdT/+b1kkhIqi/77uiRn64ksjZJpFU9LJ+Sq//6+eDBQfk9/PlFSG5kuqoootMZPfomI2sDnYcUjU21AkAtw2nPTNjQNLiZNNg/G5OoFsI2e7oZMXFzfEQFNFMbDMRj8lQ/1GnBfIXusJZiNTDYplR+mDrWqKjbrqqy1838";

	/** 加密公钥 */
	public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOlvlf1KCSEdBfYRKkaHv61HO5W9Z/o7Gv1rU2iMZpYhXRcZJ4cj7BRrdRDs7OdI4R3tTrUtLVylLrgb1ODP0LOPqmyNmYh3VVMbzTBoUi3MDQkZ4wsYlIeFhyD2aclF08EKZmpsYmu4EqNn8s1GdfofNxW0GNrFBGFvIqS3p/VwIDAQAB";

	/** 数字类型：1-是 */
	public static final int INT_TRUE = 1;

	/** 数字类型：0-否 */
	public static final int INT_FALSE = 0;

	/** dev配置环境 */
	public static final String PROFILE_DEV = "dev";

	/** sit配置环境 */
	public static final String PROFILE_SIT = "sit";

	/** prod配置环境 */
	public static final String PROFILE_PROD = "prod";

	/** 日志图案 */
	public static final String BASE_LOGGER = "\n"
			+ "  █████▒█    ██  ▄████▄   ██ ▄█▀     █████▒█    ██  ▄████▄   ██ ▄█▀     █████▒█    ██  ▄████▄   ██ ▄█▀\n"
			+ "▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒    ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒    ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒ \n"
			+ "▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░    ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░    ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░ \n"
			+ "░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄    ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄    ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄ \n"
			+ "░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄   ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄   ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄\n"
			+ " ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒    ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒    ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒\n"
			+ " ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░    ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░    ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░\n"
			+ " ░ ░    ░░░ ░ ░ ░        ░ ░░ ░     ░ ░    ░░░ ░ ░ ░        ░ ░░ ░     ░ ░    ░░░ ░ ░ ░        ░ ░░ ░ \n"
			+ "          ░     ░ ░      ░  ░                ░     ░ ░      ░  ░                ░     ░ ░      ░  ░   \n"
			+ "                ░                                  ░                                  ░               \n"
			+ "";

	private BaseConstant() {
	}

}
