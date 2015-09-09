package com.zanox.api.advertiser;

import javax.ws.rs.core.MultivaluedMap;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportService implements RestService {
	private final static String BASE_REST_URL = "/report/program/";
	private final static int ARGUMENTS_NUMBER = 6;
	private static String REPORT_PARAMS = "?groupby={0}&fromdate={1}&todate={2}";
	private String programId;
	private String connectId;
	private String secretKey;
	private String groupBy;

	public ReportService(String... args) {
		if (args.length != ARGUMENTS_NUMBER) {
			System.err.println("Wrong number of arguments. Correct usage: java -jar advertiser-api-client-1.0-SNAPSHOT.jar --[header|url] SERVICETYPE PROGRAM_ID CONNECT_ID SECRET_KEY GROUP_BY");
			System.exit(1);
		}

		this.programId = args[2];
		this.connectId = args[3];
		this.secretKey = args[4];
		this.groupBy = args[5];
	}

	@Override
	public String getBaseRestUrl() {
		return BASE_REST_URL + programId + getReportParams();
	}

	@Override
	public String getAuthorizationParams() throws GeneralSecurityException, UnsupportedEncodingException {
		return AuthenticationUtil.createAuthorizationUrlParams(BASE_REST_URL + programId, this.connectId, this.secretKey);
	}

	@Override
	public MultivaluedMap<String, Object> getAuthorizationHeaders() throws GeneralSecurityException, UnsupportedEncodingException{
		return AuthenticationUtil.createAuthorizationHeaders(BASE_REST_URL + programId, this.connectId, this.secretKey);
	}

	private String getReportParams() {
		if (!isGroupByValid(this.groupBy)) {
			this.groupBy = "day";
			System.out.println("Group by parameter invalid, default group by: day will be used.");
		}

		return createReportParams();
	}

	private boolean isGroupByValid(String groupBy) {
		List<String> validGroupByList = Arrays.asList("day", "month", "adspace", "admedium", "admedium,adspace", "adspace,admedium");
		return validGroupByList.contains(groupBy);
	}

	/**
	 * Fills in the parameters. The requested time range is set to 1 month
	 *
	 * @return the String containing required parameters
	 */
	private String createReportParams() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String toDate = sdf.format(addDays(now, -1));
		String fromDate = sdf.format(addDays(now, -31));
		return MessageFormat.format(REPORT_PARAMS, this.groupBy, fromDate, toDate);
	}

	private Date addDays(Date baseDate, int daysToAdd) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
		return calendar.getTime();
	}
}
