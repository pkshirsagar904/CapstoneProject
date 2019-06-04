package com.example.capSProj.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.capSProj.Model.Client;
import com.example.capSProj.Model.Messenger;
import com.example.capSProj.Model.Notification;
import com.example.capSProj.Model.Organization;
import com.example.capSProj.Model.Programs;
import com.example.capSProj.Model.Services;
import com.example.capSProj.Repository.ClientRepo;
import com.example.capSProj.Repository.MessengerRepository;
import com.example.capSProj.Repository.NotificationRepository;
import com.example.capSProj.Repository.OrgRepo;
import com.example.capSProj.Repository.ProgramsRepo;
import com.example.capSProj.Repository.ServiceRepo;
import com.example.capSProj.Services.OrganizationService;
import com.example.capSProj.Services.ProgramService;

@RestController
public class OrganizationController {

	@Autowired
	private OrganizationService orgService;

	@Autowired
	private ProgramService programService;
	
	@Autowired
	private OrgRepo oR;

	@Autowired
	private ProgramsRepo pR;

	@Autowired
	private ServiceRepo sR;

	@Autowired
	private ClientRepo cR;

	@Autowired
	private MessengerRepository mR;

	@Autowired
	private NotificationRepository nR;

	/**
	 * GetMapping used to get Http request Opens the Login Page
	 * 
	 * @return the login page
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();
		// It sets the name of the view to be resolved by the configured ViewResolver
		model.setViewName("login");
		return model;
	}

	/**
	 * Post the data to set the client on the provided services
	 * request to the organization
	 * 
	 */
	@RequestMapping(value = { "/clientlogin" })
	public ModelAndView clientRequest(@RequestParam(name = "serviceid") Integer serviceid,
			@ModelAttribute Organization organization, @ModelAttribute Services service, HttpSession session) {
		ModelAndView model = new ModelAndView("clientView");
		// It is used to iterate through all the list of Services added by organization
		List<Services> servicels = (List<Services>) sR.findAll();
		Client client2 = new Client();
		// It takes current session email to get the client information
		String clientmail = (String) session.getAttribute("email");
		// It saves the current logged in client details in client2 object
		client2 = cR.findByEmail(clientmail);

		for (Services s : servicels) {
			// It get the particular service-id which matches 'serviceid' send from the
			// client using RequestParam
			// and check for duplicates by checking if 'clientid' is not set
			if (s.getServiceid().equals(serviceid) && s.getClient() == null) {
				// Used to save all the notification values into notify object
				Notification notify = new Notification();
				// after getting the selected service, client2 object is set to that service
				s.setClient(client2);
				// used to save service record into service repository
				sR.save(s);
				int servid = s.getServiceid();
				String servname = s.getServiceName();
				String orgname = s.getServofprograms().getOrganization().getName();
				String clientName = s.getClient().getName();
				// used to set notification with following records in the table
				notify.setNotification(clientName + " has requested for " + s.getServiceName() + " service");
				notify.setServiceType("Request");
				notify.setServiceid(servid);
				notify.setOrgName(orgname);
				notify.setServiceName(servname);
				notify.setClientName(clientName);
				notify.setPending(true);
				notify.setStatusMsg("Pending");
				// SimpleDateFormat is used to set according the required format
				SimpleDateFormat formatter = new SimpleDateFormat();
				Date date = new Date();
				formatter.applyPattern("d MMM yyyy");
				// datestr contains date the request is made in String format
				String datestr = formatter.format(date);
				notify.setCreatedDate(datestr);
				notify.setPendingDate("Request made by " + clientName + " on " + datestr);
				// It is used to save the notify object into the repository;
				// Notify object contains the previous values saved
				nR.save(notify);
			} else {
				continue;
			}

		}
		return model;
	}

	/**
	 * Gets the details from login controller
	 * @return client Home page
	 */
	@GetMapping("/clientLogin")
	public ModelAndView clientLogin() {
		ModelAndView model = new ModelAndView();
		// It is used to find all the services from Service Repository
		List<Services> s = (List<Services>) sR.findAll();

		List<Services> s1 = new ArrayList<Services>();
		List<String> org = new ArrayList<String>();

		// Iterate through all the services and show them to the clientSide after Login
		for (Services ser : s) {
			s1.add(ser);
			org.add(ser.getServofprograms().getOrganization().getName());
		}
		// Used to send this 'services' object to the clientView.html
		model.addObject("services", s1);
		model.addObject("org", org);
		// It sets the name of the view to be resolved by the configured ViewResolver
		model.setViewName("clientView");
		return model;
	}

	/**
	 * Opens the PostRequest after login to the organization/client Page depending
	 * on the email
	 * @param organization
	 * @param client
	 * @return organization/client log-in
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.POST)
	public ModelAndView afterlogin(@ModelAttribute Organization organization, HttpSession session,
			@ModelAttribute Programs programs) {
		ModelAndView model = new ModelAndView();
		Client client2 = new Client();
		// Iterate through all the list of client
		List<Client> client1 = (List<Client>) cR.findAll();
		for (Client c : client1) {
			// Used to find client email entered at the login and save that object into
			// client2
			if (c.getEmail().equals(organization.getEmail())) {
				client2 = c;
				break;
			}
		}
		// Use to get the clientmail from client2 object
		String clientmail = client2.getEmail();

		if (clientmail != null) {
			ModelAndView model1 = new ModelAndView();
			session.setAttribute("email", clientmail);

			model1.setViewName("redirect:/clientLogin");
			return model1;
		}
		// get the organization email
		String emailcheck = organization.getEmail();
		// save the email into session
		session.setAttribute("email", emailcheck);

		Organization org1 = new Organization();
		// used to get the valid organization using emailcheck and save it org1
		org1 = oR.findByEmail(emailcheck);
		// It is true when incorrect login are entered
		if (org1 == null) {
			model.addObject("msg", "Incorrect Email/Password!");
			model.setViewName("redirect:/login");
		}
		// It is used to get the entered login details
		else {
			Integer orgid = oR.findByEmail(emailcheck).getOrgid();
			session.setAttribute("orgid", orgid);
			model.addObject("msg", "Successful Login!");
			model.setViewName("redirect:/profile");
		}

		return model;
	}

	/**
	 * Display RFE Messages posted by Organization iterating through the
	 * notification list to add the HashSet
	 * 
	 * @param session: 
	 * @return RFE messages added by Organization
	 */
	@GetMapping("/clientReq")
	public ModelAndView clientRequestCenter(HttpSession session) {
		ModelAndView model = new ModelAndView();
		// It is used to iterate through the list of notifications
		List<Notification> nf = (List<Notification>) nR.findAll();
		Set<Notification> notif = new HashSet<Notification>();
		for (Notification n : nf) {
			notif.add(n);
		}
		// It is used to add notif as notify on the View side
		model.addObject("notify", notif);
		// It sets the name of the view to be resolved by the configured ViewResolver
		model.setViewName("clientRequestCenter");
		return model;
	}

	/**
	 * GetMapping used to logout of the session
	 * 
	 * @param session
	 * @return session clear-out
	 */
	@GetMapping("/logout")
	public ModelAndView getlogout(HttpSession session) {
		// It clears the previous stored session
		session.invalidate();
		return new ModelAndView("redirect:/login");
	}

	/**
	 * GetMapping used to get the signup page
	 * @return signup page to fill the organization data
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView model = new ModelAndView();
		Organization org = new Organization();
		// It is used to add all the fields on the View(FrontEnd)
		model.addObject("org", org);
		model.setViewName("signup");

		return model;
	}

	/**
	 * Used to post data of the organization from the signup page
	 * @param organization
	 * @return login page after posting data
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView saveOrganization(@ModelAttribute Organization organization) {
		ModelAndView model = new ModelAndView();
		String orgExists = organization.getEmail();
		// Used to check if the entered organization exists or not
		Organization org1 = oR.findByEmail(orgExists);

		// If the org1 is set by orgExists then it means that organization exists
		if (org1 != null) {
			model.addObject("msg", "This email already exists!");
		}
		// org1 remains null
		else {
			// save 'organization' model attribute directly to organization repository
			oR.save(organization);
			model.addObject("msg", "Organization has been registered successfully!");
			model.addObject("org", new Organization());

		}
		model.setViewName("redirect:/login");
		return model;

	}
	/**
	 * Get the client signupPage details to be filled
	 * @return client signup page
	 */
	@GetMapping("/clientSignup")
	public ModelAndView createClient() {
		ModelAndView model=new ModelAndView();
		Client client=new Client();
		model.addObject("client", client);
		model.setViewName("clientSignup");
		return model;
	}
	
	/**
	 * Post the client details filled
	 * and saves into the repository 
	 * @param client
	 * @return login page 
	 */
	@PostMapping("/clientSignup")
	public ModelAndView postcreateClient(@ModelAttribute Client client) {
		ModelAndView model=new ModelAndView();
		Client clientExists=cR.findByEmail(client.getEmail());
		if(clientExists!=null) {
			model.addObject("msg", "This email already exists!");
		}
		else {
			cR.save(client);
			model.addObject("msg", "Client has been registered successfully!");
		}
		model.setViewName("redirect:/login");
		return model;
	}

	/**
	 * Used to Retrieve Organization profile information after login to organization
	 * account
	 * @param organization
	 * @param session
	 * @return organization profile
	 */
	@GetMapping("/profile")
	public ModelAndView profile(@ModelAttribute Organization organization, HttpSession session) {
		ModelAndView model = new ModelAndView();
		Organization org1 = new Organization();
		Integer orgid = (Integer) session.getAttribute("orgid");
		org1 = oR.findByOrgid(orgid);
		// Find list of notification from organization name
		List<Notification> notify = nR.findByOrgName(org1.getName());
		Set<Notification> notif = new HashSet<>();

		for (Notification n1 : notify) {
			// Notification is showed till it closed
			if (!n1.isClose()) {
				notif.add(n1);
			}
		}
		// Store list of organization specific programs into programls
		List<Programs> programls = oR.findByOrgid(orgid).getPrograms();

		// objects of list to iterate on the profile.html
		model.addObject("notify", notif);
		model.addObject("programls", programls);
		model.addObject("org1", org1);
		// Sets the viewName as 'profile.html'
		model.setViewName("profile");
		return model;
	}

	/**
	 * Used for creation of programs to the logged in organization
	 * 
	 * @param programs
	 * @param organization
	 * @param session
	 * @return program details filled up by organization
	 */
	@PostMapping("/profile")
	public ModelAndView addPrograms(@ModelAttribute Programs programs, HttpSession session) {
		ModelAndView model = new ModelAndView();
		Organization org = new Organization();
		Integer orgid = (Integer) session.getAttribute("orgid");
		org.setOrgid(orgid);
		// programs sets the logged in organization object
		programs.setOrganization(org);
		// program repository saves 'programs' object details
		pR.save(programs);
		// viewName after posting data redirects to the profile page
		model.setViewName("redirect:/profile");
		return model;
	}
	
	/**
	 * Gets the program details for editing
	 * based on the programid
	 * @param programid
	 * @param session
	 * @return editable fields for the selected program
	 */
	@GetMapping("/editProgram")
	public ModelAndView EditProgram(@RequestParam(name = "programid") Integer programid,
			HttpSession session) {
		ModelAndView model=new ModelAndView();
		Organization org1 = new Organization();
		Integer orgid = (Integer) session.getAttribute("orgid");
		org1 = oR.findByOrgid(orgid);
		List<Programs> programls = oR.findByOrgid(orgid).getPrograms();
		Programs programs=pR.findByProgramid(programid);
		model.addObject("programs", programs);
		model.addObject("programls", programls);
		model.addObject("org1", org1);
		model.setViewName("programEdit");
		return model;
	}
	
	/**
	 * Posts updated changes to the program names
	 * @param programid
	 * @param session
	 * @param programs
	 * @return updated fields for the selected program
	 */
	@PostMapping("/editProgram")
	public ModelAndView updateProgram(@RequestParam(name = "programid") Integer programid,
			HttpSession session, @ModelAttribute Programs programs) {
		ModelAndView model=new ModelAndView();
		Programs prog=pR.findByProgramid(programid);
		prog.setProgName(programs.getProgName());
		prog.setDescription(programs.getDescription());
		pR.save(prog);
		model.setViewName("redirect:/profile");
		return model;
	}
	
	
	
	/**
	 * Deletes the selected service
	 * @param serviceid
	 * @param session
	 * @return Remaining Services after deletion
	 */
	@RequestMapping("/deleteService")
	public ModelAndView deleteService(@RequestParam(name = "serviceid") Integer serviceid,
			HttpSession session) {
		ModelAndView model=new ModelAndView();
		Integer pid = (Integer) session.getAttribute("programid");
		sR.deleteById(serviceid);
		model.setViewName("progService");
		return new ModelAndView("redirect:/readmore?programid=" + pid);
		
	}
	
	
	
	/**
	 * Gets the service details for editing
	 * based on the serviceid 
	 * @param serviceid
	 * @param session
	 * @return editable fields for the selected service
	 */
	@GetMapping("/editService")
	public ModelAndView editService(
			@RequestParam(name = "serviceid") Integer serviceid,
			HttpSession session) {
		ModelAndView model=new ModelAndView();
		Integer pid = (Integer) session.getAttribute("programid");
		Programs programs=pR.findByProgramid(pid);
//		Programs prog=pR.findByProgramid(pid);
		List<Services> servicels=sR.findByserviceprogram(programs);
		Services services=sR.findByserviceid(serviceid);
		model.addObject("servicels", servicels);
		model.addObject("progName", programs.getProgName());
		model.addObject("services", services);
		model.addObject("description", programs.getDescription());
		model.setViewName("serviceEdit");
		return model;
	}
	/**
	 * Posts updated changes to the selected service
	 * @param serviceid
	 * @param session
	 * @param services
	 * @return updated fields for the selected service
	 */
	@PostMapping("/editService")
	public ModelAndView updateService(
			@RequestParam(name = "serviceid") Integer serviceid,
			HttpSession session, @ModelAttribute Services services) {
		ModelAndView model=new ModelAndView();
		Integer pid = (Integer) session.getAttribute("programid");
		Services serv=sR.findByserviceid(serviceid);
		serv.setServiceName(services.getServiceName());
		serv.setServDescription(services.getServDescription());
		sR.save(serv);
		model.setViewName("progService");
		return new ModelAndView("redirect:/readmore?programid=" + pid);
	}

	/**
	 * Used to get service data on the program profile after adding services inside
	 * program profile
	 * @param programs
	 * @param organization
	 * @param programid
	 * @param session
	 * @param services
	 * @return fields for the selected program details
	 */
	@GetMapping("/readmore")
	public ModelAndView readMore(@ModelAttribute Programs programs, @ModelAttribute Organization organization,
			@RequestParam(name = "programid") Integer programid, HttpSession session,
			@ModelAttribute Services services) {
		ModelAndView model = new ModelAndView();
		// It is used to get the programid send after selection
		Programs p1 = pR.findByProgramid(programid);
		// It is used to store organization session id into orgid
		Integer orgid = (Integer) session.getAttribute("orgid");
		// It finds the organization from organization repository and stores in
		// organization1
		Organization organization1 = oR.findByOrgid(orgid);
		List<Notification> notify = nR.findByOrgName(organization1.getName());
		Set<Notification> notif = new HashSet<>();
		// It iterates through organization specific notifications
		for (Notification n1 : notify) {
			if (!n1.isClose()) {
				notif.add(n1);
			}
		}
		// Iterate service repository and store all the services into list of Service
		// 's'
		List<Services> s = (List<Services>) sR.findAll();
		List<Services> s1 = new ArrayList<Services>();
		// Iterate through services 's' and match the programid
		for (Services ser : s) {
			if (ser.getServofprograms().getProgramid().equals(p1.getProgramid())) {
				// It adds Service 'ser' object to ArrayList of Service 's1'
				s1.add(ser);
				model.addObject("servicels", s1);
			}
		}
		// It adds programid to the session
		session.setAttribute("programid", p1.getProgramid());
		model.addObject("progName", p1.getProgName());
		model.addObject("notify", notif);
		model.addObject("description", p1.getDescription());
		// It sets the viewname as progService.html page
		model.setViewName("progService");
		return model;
	}

	/**
	 * Used to post program-services data from the program profile
	 * 
	 * @param organization
	 * @param services
	 * @param session
	 * @return program profile for updated services list
	 */
	@PostMapping("/readmore")
	public ModelAndView addServices(@ModelAttribute Organization organization, @ModelAttribute Services services,
			HttpSession session) {
		ModelAndView model = new ModelAndView();
		// It retrieves the programid stored in the session
		Integer pid = (Integer) session.getAttribute("programid");
		// It finds the program from program repository and stores in p1 object
		Programs p1 = pR.findByProgramid(pid);
		// It adds the p1 object to the service table
		services.setServofprograms(p1);
		// It saves services into service Repository
		sR.save(services);
		// It gets the list of services from the currently session program
		List<Services> service = p1.getSelectedservices();

		model.addObject("programid", p1.getProgramid());
		model.addObject("servicels", service);
		model.setViewName("redirect:/readmore");
		return model;
	}

	/**
	 * Used to Edit Profile makes the fields to be editable
	 * 
	 * @param organization
	 * @param session
	 * @return updated profile
	 */
	@GetMapping("/profedit")
	public ModelAndView edit(@ModelAttribute Organization organization, HttpSession session) {
		ModelAndView model = new ModelAndView();
		// get the organization id from the session
		Integer orgid = (Integer) session.getAttribute("orgid");
		Organization organization1 = oR.findByOrgid(orgid);
		List<Notification> notify = nR.findByOrgName(organization1.getName());
		Set<Notification> notif = new HashSet<>();
		// It is used to add organization specific notification
		for (Notification n1 : notify) {
			if (!n1.isClose()) {
				notif.add(n1);
			}
		}

		model.addObject("organization", organization1);
		model.addObject("notify", notif);
		// Sets the viewName of the page as 'profedit.html'
		model.setViewName("profedit");
		return model;
	}

	/**
	 * Post changes to profile
	 * 
	 * @param organization
	 * @param session
	 * @return profile
	 */
	@PostMapping("/profedit2")
	public ModelAndView postedit(@ModelAttribute Organization organization, HttpSession session) {

		ModelAndView model = new ModelAndView();
		Organization org = new Organization();
		// It gets the organization email from the session
		String emailcheck = (String) session.getAttribute("email");
		// It finds the organization from organization repository and stores in 'org'
		// object
		org = oR.findByEmail(emailcheck);
		// Saves the changes made on the edit
		org.setName(organization.getName());
		org.setMission(organization.getMission());
		org.setKeywords(organization.getKeywords());
		org.setEmployerIdentificationNumber(organization.getEmployerIdentificationNumber());
		org.setAliases(organization.getAliases());
		org.setGeographicArea(organization.getGeographicArea());
		org.setLanguageSpoken(organization.getLanguageSpoken());
		org.setOtherLocations(organization.getOtherLocations());
		org.setCity(organization.getCity());
		org.setState(organization.getState());
		org.setZipCode(organization.getZipCode());
		org.setAddress(organization.getAddress());
		org.setFax(organization.getFax());
		org.setHours(organization.getHours());
		org.setAboutUs(organization.getAboutUs());
		org.setPhone(organization.getPhone());
		org.setWebAddress(organization.getWebAddress());
		// saves the 'org' object into organization repository after changes being saved
		oR.save(org);
		model.addObject("organization", org);
		// redirects to the profile on the update click
		model.setViewName("redirect:/profile");
		return model;
	}
	
	
	/**
	 * Opens up the group messaging for client
	 * @param session
	 * @return chat forum
	 */
	@RequestMapping("/defect-details")
	public ModelAndView defectDetails(HttpSession session) {
		ModelAndView model = new ModelAndView();		
		String emailcheck = (String) session.getAttribute("email");
		Client client = cR.findByEmail(emailcheck);
		model.addObject("client", client);
		model.setViewName("index");
		return model;
	}
	
	

	/**
	 * Used to Open Organization names on the client after clicking the message
	 * button on the navbar
	 * 
	 * @param messenger
	 * @return Message receiver organization name
	 */
	@GetMapping("/startMessenger")
	public ModelAndView startCommunication(@ModelAttribute Messenger messenger, HttpSession session) {
		ModelAndView model = new ModelAndView();
		// Iterate through the list of the organization to be messaged
		List<Organization> org = (List<Organization>) oR.findAll();
		List<Organization> org1 = new ArrayList<Organization>();
		for (Organization o : org) {
			org1.add(o);
		}
		// saves the list of organization on the 'org1' object
		model.addObject("org1", org1);
		model.setViewName("messengerPrototype");
		return model;
	}

	/**
	 * Gets the message history towards selected Organization after clicking 'send
	 * message'
	 * 
	 * @param orgid
	 * @param messenger
	 * @param session
	 * @return Typed messages by client
	 */
	@GetMapping("/startMessenger2")
	public ModelAndView startComm(@RequestParam(name = "orgid") int orgid, @ModelAttribute Messenger messenger,
			HttpSession session) {
		ModelAndView model = new ModelAndView();
		// It finds the organization with orgid send after posting the message
		Organization orgz = oR.findByOrgid(orgid);
		// Stores the organization id into org1
		int org1 = orgz.getOrgid();
		// Iterate through the list of messages
		List<Messenger> msg = (List<Messenger>) mR.findAll();
		List<Messenger> m1 = new ArrayList<Messenger>();

		// Gets the client from the session email
		String emailcheck = (String) session.getAttribute("email");
		// It finds the client from client repository and stores into client object
		Client client = cR.findByEmail(emailcheck);

		// Iterates through message and checks appropriate sender and receiver for
		// showing the sender on the right side and receiver's message on the left and
		// adds to Arraylist of m1
		for (Messenger m : msg) {
			if ((m.getReceiver().equals(orgz.getName()) && m.getSender().equals(client.getName()))
					|| (m.getReceiver().equals(client.getName()) && m.getSender().equals(orgz.getName()))) {
				m1.add(m);
			}
		}

		model.addObject("msgls", m1);
		model.addObject("org1", org1);
		model.addObject("rightName", client.getName());
		// Sets the view as 'addChatContent.html'
		model.setViewName("addChatContent");

		return model;
	}

	/**
	 * Post Messages towards Particular organization using orgid and stores the data
	 * on the message box
	 * 
	 * @param orgid
	 * @param messenger
	 * @param session
	 * @return all the message exchanges between client and particular organization
	 */
	@PostMapping("/startMessenger2")
	public ModelAndView startChatting(@RequestParam(name = "orgid") int orgid, @ModelAttribute Messenger messenger,
			HttpSession session) {
		ModelAndView model = new ModelAndView();
		// finds the clientemail from the session
		String emailcheck = (String) session.getAttribute("email");
		// finds the client from the clientRepository
		Client client = cR.findByEmail(emailcheck);
		// Used to save newly typed messages
		Messenger msg = new Messenger();
		// Setting the client name as the message sender
		msg.setSender(client.getName());
		// finds the instance of organization using the selected orgid
		Organization o = oR.findByOrgid(orgid);
		// stores the organizationid into org1
		int org1 = o.getOrgid();
		// Used to set the receiver as selected organization name
		msg.setReceiver(o.getName());
		// check if the message is being typed then the date is set to the message
		if (messenger.getTypedContent() != null) {
			msg.setTypedContent(messenger.getTypedContent());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String s = dtf.format(now);
			msg.setDate(s);
		}
		// Saves the data saved in 'msg' into message repository
		mR.save(msg);

		model.addObject("org1", org1);
		model.setViewName("addChatContent");
		return new ModelAndView("redirect:/startMessenger2?orgid=" + orgid);
	}

	/**
	 * Get client names which messaged a particular organization display names of
	 * clients who messaged the logged in organization
	 * 
	 * @param session
	 * @return active clients list
	 */
	@GetMapping("/receiveMessenger")
	public ModelAndView receiveMsg(HttpSession session) {
		ModelAndView model = new ModelAndView();
		// Retrieves the organization-id from the session
		Integer orgid = (Integer) session.getAttribute("orgid");
		// Stores the organization from the session into organization1 object
		Organization organization1 = oR.findByOrgid(orgid);
		List<Notification> notify = nR.findByOrgName(organization1.getName());
		// Used to display notification specific to organization
		Set<Notification> notif = new HashSet<>();
		for (Notification n1 : notify) {
			if (!n1.isClose()) {
				notif.add(n1);
			}
		}

		// Iterate through the list of messages from the messenger model
		List<Messenger> msg = (List<Messenger>) mR.findAll();
		// Used to stored the messages to display on the view
		List<Messenger> messages = new ArrayList<Messenger>();
		// Used to stored the clients to display on the view
		List<Client> client = new ArrayList<Client>();

		Messenger m1 = new Messenger();
		// Iterates through messages and check organization name
		for (Messenger m : msg) {
			if (m.getReceiver().equals(organization1.getName())) {
				m1 = m;
				messages.add(m);
			}
		}
		List<Client> cl = (List<Client>) cR.findAll();
		// checks the sender of the message and add to client object
		if (messages != null && !messages.isEmpty()) {
			for (Client c : cl) {
				if (m1 != null && m1.getSender().equals(c.getName())) {
					client.add(c);
				}
			}
		}
		model.addObject("notify", notif);
		model.addObject("msgls", messages);
		model.addObject("clientls", client);
		model.setViewName("receiveMsg");
		return model;
	}

	/**
	 * Open the message chat between client and Organization
	 * 
	 * @param cid
	 * @param session
	 * @return previous exchanges between client and Organization
	 */
	@GetMapping("/receiveMessenger2")
	public ModelAndView receiveMsg2(@RequestParam(name = "clientid") int cid, HttpSession session) {
		ModelAndView model = new ModelAndView();
		// Used to get orgid from session
		Integer orgid = (Integer) session.getAttribute("orgid");
		Organization organization1 = oR.findByOrgid(orgid);
		List<Notification> notify = nR.findByOrgName(organization1.getName());
		// Used for organization specific notification
		Set<Notification> notif = new HashSet<>();
		for (Notification n1 : notify) {
			System.out.println("checking org specific notification" + n1.getNotification());
			if (!n1.isClose()) {
				notif.add(n1);
			}
		}

		// Used to get organization using orgid
		Organization orgz = oR.findByOrgid(orgid);

		// Used to get the client object from the RequestParam(value send from FrontEnd
		// to BackEnd)
		// and retrieved from clientRepo
		Client client = cR.findByid(cid);

		// Iterates through message and checks appropriate sender and receiver for
		// showing the sender on the right side and receiver's message on the left and
		// adds to
		// Arraylist of m1
		List<Messenger> msg = (List<Messenger>) mR.findAll();
		List<Messenger> m1 = new ArrayList<Messenger>();
		for (Messenger m : msg) {
			if ((m.getReceiver().equals(orgz.getName()) && m.getSender().equals(client.getName()))
					|| (m.getReceiver().equals(client.getName()) && m.getSender().equals(orgz.getName()))) {
				m1.add(m);
			}
		}
		model.addObject("notify", notif);
		model.addObject("msgls", m1);
		model.addObject("client1", client.getId());
		model.addObject("rightName", orgz.getName());
		model.setViewName("receivechatContent");
		return model;

	}

	/**
	 * Post Messages towards Particular client using clientid and stores the data on
	 * the message box
	 * 
	 * @param cid
	 * @param messenger
	 * @param session
	 * @return all the exchanges between client and Organization
	 */
	@PostMapping("/receiveMessenger2")
	public ModelAndView receiveMsg3(@RequestParam(name = "clientid") int cid, @ModelAttribute Messenger messenger,
			HttpSession session) {
		ModelAndView model = new ModelAndView();
		Messenger msg = new Messenger();
		// Used to get orgid from session
		Integer orgid = (Integer) session.getAttribute("orgid");
		Organization sessionorg = oR.findByOrgid(orgid);
		// Setting the organization name as the message sender
		msg.setSender(sessionorg.getName());

		// Used to get the client object from the RequestParam(value send from FrontEnd
		// to BackEnd)
		// and retrieved from clientRepo
		Client c = cR.findByid(cid);
		// Setting the client name as the message receiver
		msg.setReceiver(c.getName());

		// check if the message is being typed then the date is set to the message
		if (messenger.getTypedContent() != null) {
			msg.setTypedContent(messenger.getTypedContent());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String s = dtf.format(now);
			msg.setDate(s);
		}
		// Saves the data saved in 'msg' into message repository
		mR.save(msg);
		model.setViewName("receivechatContent");
		return new ModelAndView("redirect:/receiveMessenger2?clientid=" + cid);
	}

	/**
	 * Gets all the requests made by client Displays List of Requests
	 * 
	 * @param programs
	 * @param session
	 * @return requests made by the client
	 */
	@GetMapping("/requestCenter")
	public ModelAndView requestCenter(@ModelAttribute Programs programs, HttpSession session) {
		ModelAndView model = new ModelAndView();
		// Used to get orgid from session
		Integer orgid = (Integer) session.getAttribute("orgid");
		Organization sessionorg = oR.findByOrgid(orgid);
		List<Organization> org = (List<Organization>) oR.findAll();
		List<Organization> org1 = new ArrayList<Organization>();
		// Iterate all the list of organization
		// and check the non-matching organization
		for (Organization o : org) {
			if (!o.equals(sessionorg)) {

				org1.add(o);
			}
		}

		List<Notification> notify = (List<Notification>) nR.findAll();
		Set<Notification> notif = new HashSet<Notification>();
		// It iterates all the notification from 'notify' objects
		// and adds notification to notif as per conditions
		for (Notification n1 : notify) {
			// Gets the notification to both organization referral org and non referral org
			if (n1.getReferralOrg() != null && n1.getReferralOrg().equals(n1.getOrgName())) {
				notif.add(n1);
			} else if (n1.getOrgName().equals(sessionorg.getName()) && n1.isPending()) {
				notif.add(n1);
			} else if (n1.isAcknowledged()) {

				notif.add(n1);
			} else if (n1.isInProgress()) {
				notif.add(n1);
			}

			else if (n1.isClose()) {
				notif.add(n1);
			}

			else if (n1.isDecline()) {
				notif.add(n1);
			} else if (n1.isRfe()) {
				notif.add(n1);
			}

		}
		model.addObject("notify", notif);
		model.addObject("org1", org1);
		// sets the viewName as 'requestCenter.html'
		model.setViewName("requestCenter");
		return model;
	}

	/**
	 * Gets the referral data based on the particular organization
	 * 
	 * @param notificationid
	 * @param session
	 * @return selected organization for referral
	 */
	@GetMapping("/setReferral")
	public ModelAndView referralcheck(@RequestParam(name = "id") int notificationid, HttpSession session) {
		ModelAndView model = new ModelAndView();
		// Used to get orgid from session
		Integer orgid = (Integer) session.getAttribute("orgid");
		Organization sessionorg = oR.findByOrgid(orgid);
		List<Organization> org = (List<Organization>) oR.findAll();
		List<Organization> org1 = new ArrayList<Organization>();
		// Iterate all the list of organization
		// and check the non-matching organization
		for (Organization o : org) {
			if (!o.equals(sessionorg)) {
				org1.add(o);
			}
		}

		// Check the matching notification based on notificationid from
		// NotificationRepository
		Notification notifyDisplay = nR.findByid(notificationid);

		// Adds notificationid to session
		session.setAttribute("notificationid", notificationid);
		model.addObject("notifyDisplay", notifyDisplay);
		model.addObject("org1", org1);
		// sets the viewName as 'applyReferral.html'
		model.setViewName("applyReferral");
		return model;

	}

	/**
	 * Sets the Organization for Referral
	 * 
	 * @param session
	 * @param orgName
	 * @param nid
	 * @param notification
	 * @return chosen organization after selection for referral
	 */
	@PostMapping("/requestCenter")
	public ModelAndView referralRetrieve(HttpSession session, @RequestParam(value = "orgselection") String orgName,
			@RequestParam(value = "notificationid") String nid, @ModelAttribute Notification notification) {
		ModelAndView model = new ModelAndView();

		List<Notification> notif = (List<Notification>) nR.findAll();
		for (Notification n : notif) {
			// matches the notification based on notificationid
			if (n.getId() == Integer.parseInt(nid)) {
				// sets the referralOrgname based on orgName(RequestParam)
				n.setReferralOrg(orgName);
				// Sets the referred message
				n.setStatusMsg("Referred to " + orgName);
				// sets the organization name
				n.setOrgName(orgName);
				// sets the serviceType as Referral
				n.setServiceType("Referral");
				// Saves the notification object 'n' to NotificationRepository
				nR.save(n);
			}
		}
		model.setViewName("redirect:/requestCenter");
		return model;
	}

	/**
	 * Retrieves selected notification data
	 * 
	 * @param notificationid
	 * @param session
	 * @return Timeline and actions to be performed for the notification
	 */
	@GetMapping("/noticationTimeline")
	public ModelAndView notificationCenter(@RequestParam(name = "id") int notificationid, HttpSession session) {
		ModelAndView model = new ModelAndView();
		// Used to get orgid from session
		Integer orgid = (Integer) session.getAttribute("orgid");
		Organization organization1 = oR.findByOrgid(orgid);
		// finds notification based on organization
		List<Notification> notify1 = nR.findByOrgName(organization1.getName());
		Set<Notification> notif = new HashSet<>();
		for (Notification n1 : notify1) {
			if (!n1.isClose()) {
				notif.add(n1);
			}
		}

		// Check the matching notification based on notificationid from
		// NotificationRepository
		Notification notifyDisplay = nR.findByid(notificationid);

		// adds notificationid to session
		session.setAttribute("notificationid", notificationid);

		model.addObject("notify", notif);
		model.addObject("notifyDisplay", notifyDisplay);
		model.setViewName("notificationDisplayCenter");

		return model;
	}

	/**
	 * Post actions for the selected notification based on notificationid
	 * 
	 * @param session
	 * @param data
	 * @param notification
	 * @return notification actions on selection
	 */
	@PostMapping("/noticationTimeline")
	public ModelAndView notificationStatusCenter(HttpSession session,
			@RequestParam(value = "data", required = false) String data, @ModelAttribute Notification notification) {
		ModelAndView model = new ModelAndView();
		// gets the notificationid from session
		int nid = (int) session.getAttribute("notificationid");

		List<Notification> notif = (List<Notification>) nR.findAll();
		for (Notification n : notif) {
			// checks for notificationid from notification Repository
			if (n.getId() == nid) {
				String orgName = n.getOrgName();
				String clientName = n.getClientName();
				// if the 'data' from dropdown Actions is send
				if (data != null) {
					// checks send 'data' equals acknowledge
					if (data.equals("Acknowledge")) {
						n.setPending(false);
						n.setAcknowledged(true);
						n.setStatusMsg("Acknowledged");
						SimpleDateFormat formatter = new SimpleDateFormat();
						Date date = new Date();
						formatter.applyPattern("d MMM yyyy");
						String s = formatter.format(date);

						n.setAcknowledgeDate("Request acknowledged by " + orgName + " for " + clientName + " on " + s);
					}
					// checks send 'data' equals InProgress
					if (data.equals("InProgress")) {
						n.setAcknowledged(false);
						n.setInProgress(true);
						n.setStatusMsg("InProgress");

						SimpleDateFormat formatter = new SimpleDateFormat();
						Date date = new Date();
						formatter.applyPattern("d MMM yyyy");
						String s = formatter.format(date);
						n.setInProgressDate("Request InProgress by " + orgName + " for " + clientName + " on " + s);

					}
					// checks send 'data' equals close
					if (data.equals("Close")) {
						n.setInProgress(false);
						n.setClose(true);
						n.setStatusMsg("Closed");

						SimpleDateFormat formatter = new SimpleDateFormat();
						Date date = new Date();
						formatter.applyPattern("d MMM yyyy");
						String s = formatter.format(date);
						n.setCloseDate("Request Closed by " + orgName + " for " + clientName + " on " + s);

					}
					// checks send 'data' equals Decline
					if (data.equals("Decline")) {
						n.setInProgress(false);
						n.setDecline(true);
						n.setStatusMsg("Declined");
						SimpleDateFormat formatter = new SimpleDateFormat();
						Date date = new Date();
						formatter.applyPattern("d MMM yyyy");
						String s = formatter.format(date);
						n.setDeclineDate("Request Declined by " + orgName + " for " + clientName + " on " + s);

					}
				}
				// checks RFE for null
				if (notification.getRfeMsg() != null) {
					n.setAcknowledged(false);
					n.setInProgress(false);
					n.setClose(false);
					n.setDecline(false);
					n.setRfe(true);
					n.setStatusMsg("RFE");
					n.setRfeMsg(notification.getRfeMsg());

					SimpleDateFormat formatter = new SimpleDateFormat();
					Date date = new Date();
					formatter.applyPattern("d MMM yyyy");
					String s = formatter.format(date);
					n.setRfeDate("Request RFE by " + orgName + " for " + clientName + " on " + s);

				}
				nR.save(n);

			}

		}
		model.setViewName("requestCenter");
		return new ModelAndView("redirect:/noticationTimeline?id=" + nid);
	}

}