package com.bae.moumyah.ghost;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class GhostService {

	private static final Logger logger = LoggerFactory.getLogger(GhostService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
    
    @Autowired
    GhostSystemComponent ghostSystemComponent;
    
    
	@Value("${console.server_url}")
    private String postUrl;
	
	@Value("${console.client_id}")
    private int clientId;
	
	@Value("${console.cluster_name}")
    private String clusterName;
	
	
	
	
	
	String[] ghostComandLine = new String[] {
			"gh-ost",                                    // 0
			"--max-load=Threads_running=50",             // 1
			"--critical-load=Threads_running=1500",      // 2
			"--chunk-size=500",                          // 3
			"--max-lag-millis=1500",                     // 4
			"--conf=/etc/mysql/debian.cnf",              // 5
			"--host=127.0.0.1",                          // 6
			"--throttle-control-replicas=",              // 7
			"--database=",                               // 8
			"--table=",                                  // 9
			"--alter=",                                  // 10
			"--switch-to-rbr",                           // 11
			"--cut-over=default",                        // 12
			"--exact-rowcount",                          // 13
			"--concurrent-rowcount",                     // 14
			"--default-retries=120",                     // 15
			"--timestamp-old-table",                     // 16
			""                                           // 17
	};
	
	/*
	public ArrayList<String> getGhostValidateByTable(String tableName) {
		
		ArrayList<String> databases = new ArrayList<String>();
		
		databases.add("a_company");
		databases.add("b_company");
		
		logger.debug("------------------------ DEBUG -------------------");
    	logger.debug(tableName);
    	
    	
		return  databases;
	}
	
	
	public GhostResultDTO getGhostDTO(GhostQueryDTO ghostQueryDTO) {
		
		GhostResultDTO ghostDTO = new GhostResultDTO();
		
		logger.debug("------------------------ DEBUG -------------------");
    	logger.debug(ghostDTO.toString());
    	
		GhostHostDTO ghostHostDTO = this.getGhostHostDTO();
		GhostMySQLDTO ghostMySQLDTO = this.getGhostMySQLDTO();
		
		ghostDTO.setDatabaseName(ghostQueryDTO.getDatabaseName());
		ghostDTO.setTableName(ghostQueryDTO.getTableName());
		
		ghostDTO.setId(clientId);
		ghostDTO.setFileName(ghostHostDTO.getFileName());
		ghostDTO.setTableFileSize(ghostHostDTO.getTableFileSize());
		ghostDTO.setTableDescription(ghostMySQLDTO.getTableDescription());
		ghostDTO.setTableCadinality(ghostMySQLDTO.getTableCadinality());

		logger.debug(ghostDTO.toString());
		return ghostDTO;
		
	}
	
	
	public GhostHostDTO getGhostHostDTO() {
		
		GhostHostDTO ghostHostDTO = new GhostHostDTO();
		
		ghostHostDTO.setId(clientId);
		ghostHostDTO.setFileName("/mysql/data/a_company/order.ibd");
		ghostHostDTO.setTableFileSize(2342352352342L);
		
		
		return ghostHostDTO ;
	}
	
	
	public GhostMySQLDTO getGhostMySQLDTO() {
		
		GhostMySQLDTO ghostMySQLDTO = new GhostMySQLDTO();
		
		ghostMySQLDTO.setId(clientId);
		ghostMySQLDTO.setTableDescription("CREATE TABLE \n ( a int ...\n b ..");
		ghostMySQLDTO.setTableCadinality(123123123123123L);
		
		return ghostMySQLDTO;
	}
	
	*/
	
	public GhostAlterDTO validation(GhostAlterDTO ghostAlterDTO) {
		
		int validationCode = ghostSystemComponent.validateBeforeRun();
		
		
		/*
		 * //code 0: init
		 * //code 1: postphone file
		 * //code 2: ghost process
		 * //code 3: ghost sock files 
		 * 
		 * // code 101: OK 
		 */
		ghostAlterDTO.setValidationCode(validationCode);
		
		
		if (validationCode == 101) {
			ghostAlterDTO.setValicationPass(true);
		}else {
			ghostAlterDTO.setValicationPass(false);
		}  
		
		return ghostAlterDTO;
		
	}
	
	
	public GhostAlterDTO ghostDryRun(GhostAlterDTO ghostAlterDTO) {
		
		ghostAlterDTO.setOutputStrList(this.ghostRun(ghostAlterDTO, "--verbose"));
		
		boolean isFatal = ghostAlterDTO.getOutputStrList().stream().anyMatch( e -> e.contains("FATAL"));
		
		logger.debug("Ghost dryrun FATAL : "+isFatal);
		
		if(isFatal) {
			ghostAlterDTO.setValidationCode(202);
			ghostAlterDTO.setValidationMessage("Dry run Fatal found.");
			ghostAlterDTO.setValicationPass(false);
		}else {
			ghostAlterDTO.setValidationCode(101);
			ghostAlterDTO.setValidationMessage("Dry run successful.");
			ghostAlterDTO.setValicationPass(true);
		}
		
		return ghostAlterDTO;
		
	}
	
	
	
	
	public GhostAlterDTO ghostExecute(GhostAlterDTO ghostAlterDTO) {
				
		ghostAlterDTO.setOutputStrList(this.ghostRun(ghostAlterDTO, "--execute"));
		
		boolean isFatal = ghostAlterDTO.getOutputStrList().stream().anyMatch( e -> e.contains("FATAL"));
		
		logger.debug("Ghost execute FATAL : "+isFatal);
		
		if(isFatal) {
			ghostAlterDTO.setValidationCode(202);
			ghostAlterDTO.setValidationMessage("Execution Fatal found.");
			ghostAlterDTO.setValicationPass(false);
		}else {
			ghostAlterDTO.setValidationCode(101);
			ghostAlterDTO.setValidationMessage("Execution successful.");
			ghostAlterDTO.setValicationPass(true);
		}
		
		return ghostAlterDTO;
		
	}
	
	
	
	
	private List<String> ghostRun(GhostAlterDTO ghostAlterDTO, String verbose) {
		
		
		
		StringBuilder database      = new StringBuilder("--database=");
		StringBuilder table         = new StringBuilder("--table=");
		StringBuilder alterStatment = new StringBuilder("--alter=");
		StringBuilder checkReplica  = new StringBuilder("--throttle-control-replicas=");
		
		
		logger.debug("cmd option length: "+ghostComandLine.length);
		
		/*
		 * database
		 */
		database.append(ghostAlterDTO.getDatabaseName());

		
		/*
		 * table
		 */
		table.append(ghostAlterDTO.getTableName());
		
		/*
		 * checkReplica
		 */
		ArrayList<String> checkReplicaList = ghostAlterDTO.getCheckReplicaList();
		for(int i = 0; i < checkReplicaList.size(); i++) {			
			if (i != 0) checkReplica.append(",");
			checkReplica.append(checkReplicaList.get(i));
		}
		
		
		/*
		 * alterStatment
		 */
		ArrayList<String> alterStatementList = ghostAlterDTO.getAlterStatement();
		for(int i = 0; i < alterStatementList.size(); i++) {			
			if (i != 0) alterStatment.append(",");
			alterStatment.append(alterStatementList.get(i));
		}
		
		
		
		for(int i= 0; i < ghostComandLine.length; i++) {
			
			if(i == 8) {        ghostComandLine[i] = database.toString();
			}else if (i == 9) { ghostComandLine[i] = table.toString();
			}else if (i == 7) { ghostComandLine[i] = checkReplica.toString();
			}else if (i == 10){ ghostComandLine[i] = alterStatment.toString();
			}else if (i == 17){ ghostComandLine[i] = verbose;
			}
		
			logger.info(ghostComandLine[i]);
		}
		
		
		return ghostSystemComponent.runProcess(ghostComandLine);
		
	
		
	}
	
	
	
	
	
	
	
	
	
	
}
