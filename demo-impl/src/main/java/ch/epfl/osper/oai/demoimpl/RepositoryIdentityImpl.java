package ch.epfl.osper.oai.demoimpl;

import ch.epfl.osper.oai.interfaces.RepositoryIdentity;

import javax.inject.Named;

/**
 * Created by kryvych on 16/10/15.
 */
@Named
public class RepositoryIdentityImpl implements RepositoryIdentity {
    @Override
    public String baseURL() {
        return "http://localhost:8095/oai";
    }

    @Override
    public String repositoryName() {
        return "Demo repository";
    }

    @Override
    public String adminEmail() {
        return "admin@admin.com";
    }

    @Override
    public String deletedRecords() {
        return "transient";
    }

    @Override
    public String granularity() {
        return "yyyy-MM-dd'T'HH:mm:ss Z";
    }
}
