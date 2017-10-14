package org.jqassistant.contrib.plugin.structurizr;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.Location;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

public class TechTribes {

    public static void main(String[] args) throws Exception {
        Workspace workspace = createTechTribesWorkspace();


        // and upload the model to structurizr.com
        StructurizrClient structurizrClient = new StructurizrClient("https://api.structurizr.com", "key", "secret");
        workspace.setId(1234); // this would be your workspace ID
        structurizrClient.putWorkspace(1234,workspace);
    }

    public static Workspace createTechTribesWorkspace() {
        // create a model and the software system we want to describe
        Workspace workspace = new Workspace("techtribes.je", "This is a model of the system context for the techtribes.je system, the code for which can be found at https://github.com/techtribesje/techtribesje");
        Model model = workspace.getModel();

        SoftwareSystem techTribes = model.addSoftwareSystem(Location.Internal, "techtribes.je", "techtribes.je is the only way to keep up to date with the IT, tech and digital sector in Jersey and Guernsey, Channel Islands");

        // create the various types of people (roles) that use the software system
        Person anonymousUser = model.addPerson(Location.External, "Anonymous User", "Anybody on the web.");
        anonymousUser.uses(techTribes, "View people, tribes (businesses, communities and interest groups), content, events, jobs, etc from the local tech, digital and IT sector.");

        Person authenticatedUser = model.addPerson(Location.External, "Aggregated User", "A user or business with content that is aggregated into the website.");
        authenticatedUser.uses(techTribes, "Manage user profile and tribe membership.");

        Person adminUser = model.addPerson(Location.External, "Administration User", "A system administration user.");
        adminUser.uses(techTribes, "Add people, add tribes and manage tribe membership.");

        // create the various software systems that techtribes.je has a dependency on
        SoftwareSystem twitter = model.addSoftwareSystem(Location.External, "Twitter", "twitter.com");
        techTribes.uses(twitter, "Gets profile information and tweets from.");

        SoftwareSystem gitHub = model.addSoftwareSystem(Location.External, "GitHub", "github.com");
        techTribes.uses(gitHub, "Gets information about public code repositories from.");

        SoftwareSystem blogs = model.addSoftwareSystem(Location.External, "Blogs", "RSS and Atom feeds");
        techTribes.uses(blogs, "Gets content using RSS and Atom feeds from.");

        // now create the system context view based upon the model
        ViewSet viewSet = workspace.getViews();
        SystemContextView contextView = viewSet.createSystemContextView(techTribes,"techtribes","description");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();
        return workspace;
    }

}
