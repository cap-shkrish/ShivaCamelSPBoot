package SpringBootDemo.LMSRouter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RouterDBService {
	
	private String getActivityType(String activityCode) throws SQLException {
		return "";
	
	}
	
	private Map<String, String> getListOfLabs(String userId) throws SQLException
	{
		//Call some stored procedure
		return null;
	}
	
	private void claimKit(String labNumber) throws IOException
	{
		
	}

	private ArrayList<String> getMailingCodes()
	{
		return null;
	}
	
	private Map<String, String> getActiveKitsFromScores()
	{
		return null;
	}
	
	private String assignKitToUser(String labNumber, String[] kitNumbers, String mailingCode)
	{
		return null;
	}
	
	private Map<String, String> findLabsHavingKits()
	{
		return null;
	}
	
	private boolean isOpenKit(String kitNumber)
	{
		return false;
	}
	private  Map<String, String> getPeerLabsFromScores(String labNumber) throws SQLException {
		return null;
	}
	
	private int getCountOfUserOrgAssoc() throws SQLException
	{
		//Call some stored procedure
        String getListOfLabsByPartyNumber =
                "{call XXCAP.XXCAP_LMS_UTIL_PKG.GET_RELATED_ORGS(?,?)}";
              //callableStatement = ebsConnection.prepareCall(getListOfLabsByPartyNumber);
		return 0;
	}

}
