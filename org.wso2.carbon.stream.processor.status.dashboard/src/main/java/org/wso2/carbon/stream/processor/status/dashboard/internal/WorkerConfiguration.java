package org.wso2.carbon.stream.processor.status.dashboard.internal;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.*;
import org.osgi.service.jndi.JNDIContextManager;
import org.wso2.carbon.datasource.core.api.DataSourceManagementService;
import org.wso2.carbon.datasource.core.api.DataSourceService;
import org.wso2.carbon.datasource.core.beans.DataSourceMetadata;
import org.wso2.carbon.datasource.core.exception.DataSourceException;
import org.wso2.carbon.stream.processor.status.dashboard.model.Worker;

import java.sql.*;

@Component(
        name = "org.wso2.carbon.stream.processor.status.dashboard.datasource",
        immediate = true
)
public class WorkerConfiguration {

    private static final Log logger = LogFactory.getLog(WorkerConfiguration.class);
    static Connection connection = null;

    @Activate
    protected void start(BundleContext bundleContext) {
    }

    @Reference(
            name = "org.wso2.carbon.datasource.DataSourceService",
            service = DataSourceService.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unregisterDataSourceService"
    )
    protected void onDataSourceServiceReady(DataSourceService service) {

        try {
            HikariDataSource dsObject = (HikariDataSource) service.getDataSource("WSO2_SP_WORKER_DB");
            //HikariRDBMSDataSource

            connection = dsObject.getConnection();
            //logger.info("Database Major Version {}", connection.getMetaData().getDatabaseMajorVersion());

            //From connection do the required CRUD operation
            //addWorker(connection);

        } catch (DataSourceException e) {
            logger.error("error occurred while fetching the data source.", e);
        } catch (SQLException e) {
            logger.error("error occurred while fetching the connection.", e);
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    logger.error("error occurred while closing the connection.", e);
//                }
//            }
       }
    }

    public static int addWorker(Worker worker){

        logger.info("addWorker method fired!...............................................................................................");

        //Connection connection = null;
        int result;

        try {

            String query = " insert into worker_config (workerID, clusterID, username, password, url)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, worker.getId());
            preparedStmt.setString (2, worker.getClusterID());
            preparedStmt.setString (3, worker.getUsername());
            preparedStmt.setString (4, worker.getPassword());
            preparedStmt.setString (5, worker.getUrl());

            // execute the preparedstatement
            result = preparedStmt.executeUpdate();
            logger.info("addWorker result is : "+result);
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            logger.error("error occurred while fetching the connection.", e);
            result=0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("error occurred while closing the connection.", e);
                }
            }
        }

        return result;
    }

    public void getWorkerById(){

    }

    public void updateWorker(){

    }

    public void deleteWorker(){

    }

    public boolean validateWorker(String workerID) {
        Statement stmt = null;
        ResultSet rs;
        Connection connection=null;
        boolean isExists = false;

        try {
            String query = "SELECT * FROM worker_config WHERE workerID='" +workerID+ "'";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

//            while (rs.next()) {
//                String name = rs.getString("NAME");
//                String value = rs.getString("VALUE");
//                logger.info("---->> " + name + ":" + value);
//            }

            if(rs!=null){
                isExists = true;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
           logger.error("Error occurred while fetching the connection.",e);
           rs.close();
           stmt.close();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("error occurred while closing the connection.", e);
                }
            }
        }

    }

    @Reference(
            name = "org.wso2.carbon.datasource.DataSourceManagementService",
            service = DataSourceManagementService.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unregisterDataSourceManagementService"
    )
    protected void onDataSourceManagementServiceReady(DataSourceManagementService service) {
        logger.info("Sample bundle register method fired");
        try {
            DataSourceMetadata metadata = service.getDataSource("WSO2_SP_WORKER_DB");
            logger.info(metadata.getName());
            //You can perform your functionalities by using the injected service.
        } catch (DataSourceException e) {
            logger.error("Error occurred while fetching the data sources", e);
        }
    }

    protected void onJNDIUnregister(JNDIContextManager jndiContextManager) {
        logger.info("Unregistering data sources sample");
    }

    protected void unregisterDataSourceService(DataSourceService dataSourceService) {
        logger.info("Unregistering data sources sample");
    }

    protected void unregisterDataSourceManagementService(DataSourceManagementService dataSourceManagementService) {
        logger.info("Unregistering data sources sample");
    }
}
