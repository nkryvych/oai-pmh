package ch.epfl.gsn.oai.interfaces;

/**
 * Created by kryvych on 16/10/15.
 */
public interface RepositoryIdentity {

    String baseURL();
    String repositoryName();
    String adminEmail();
    String deletedRecords();
    String granularity();

}
