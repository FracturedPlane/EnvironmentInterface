package aiInterface.environment;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import aiInterface.data.*;
import aiInterface.handler.AgentConnectionHandler;
import aiInterface.handler.EntityConnectionHandler;
import aiInterface.intreface.Agent;
import aiInterface.intreface.Entity;
import aiInterface.listener.EnvironmentListener;

import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.EnvironmentInterfaceException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;


/**
 * This class represents the default-implementation for <code>EnvironmentInterfaceStandard</code>.
 * <p/>
 * This class implements these functionalities of <code>EnvironmentInterfaceStandard</code>:
 * <ul>
 * <li>attaching and detaching listeners;</li>
 * <li>registering and unregistering agents;</li>
 * <li>managing the agents-entities-relationship;</li>
 * <li>performing actions and retrieving percepts;</li>
 * </ul>
 * <p/>
 * It also implements these:
 * <ul>
 * <li>notifying listeners;</li>
 * <li>adding and removing entities.</li>
 * </ul>
 * @author tristanbehrens
 *
 */
public abstract class UnrealEI implements EnvironmentInterfaceStandard,Serializable 
{

	public static final String CHARSET= "UTF-8";
	public static final String GAME_OVER = "GAMEOVER";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7236450842451619679L;

	/**
	 * This is a list of registered agents.
	 * <p/>
	 * Only registered agents can act and be associated with entities.
	 */
	private LinkedList<Agent> registeredAgents = null;

	/**
	 * This is a list of entities.
	 */
	private LinkedList<Entity> entities = null;
	
	/**
	 * This is a list of entities, that are not associated with any agent.
	 */
	private LinkedList<Entity> freeEntities = null;

	/**
	 * This map stores the agents-entities-relation.
	 */
	private ConcurrentHashMap<Agent,HashSet<Entity>> agentsToEntities = null;
	
	/**
	 * This collection stores the listeners that are used to notify about certain events.
	 * <p/> 
	 * The collection can be changed by invoking the respective methods for attaching and
	 * detaching listeners.
	 */
	private Vector<EnvironmentListener> environmentListeners = null;
	
	/**
	 * Stores for each entity its respective type.
	 */
	private HashMap<Entity,String> entitiesToTypes = null;

	
	/**
	 * Instantiates the class.
	 */
	public UnrealEI()
	{
		
		environmentListeners = new Vector<EnvironmentListener>();
		
		registeredAgents 	= new LinkedList<Agent>();
		entities 			= new LinkedList<Entity>();
		freeEntities 		= new LinkedList<Entity>();
		agentsToEntities 	= new ConcurrentHashMap<Agent,HashSet<Entity>>();
		entitiesToTypes		= new HashMap<Entity,String>();
		
	}

	
	
	/*
	 * Listener functionality. Attaching, detaching, notifying listeners.
	 */

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#attachEnvironmentListener(eis.EnvironmentListener)
	 */
	public void attachEnvironmentListener(EnvironmentListener listener) 
	{
			if ( environmentListeners.contains(listener) == false)
			{
				environmentListeners.add(listener);
			}
	}

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#detachEnvironmentListener(eis.EnvironmentListener)
	 */
	public void detachEnvironmentListener(EnvironmentListener listener) 
	{
		
		if( environmentListeners.contains(listener) == true)
		{
			environmentListeners.remove(listener);
		}
	}
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#attachAgentListener(java.lang.String, eis.AgentListener)
	 */
	public void attachAgentListener(Agent agent, AgentConnectionHandler agentConnectionHandler) 
	{
		
		if( registeredAgents.contains(agent) == false )
			return;
		
		
		agent.addListener(agentConnectionHandler);
		/*
		HashSet<AgentHandler> listeners = agentsToAgentListeners.get(agent);
		
		if( listeners == null )
			listeners = new HashSet<AgentHandler>();
		
		listeners.add(listener);
		
		agentsToAgentListeners.put(agent,listeners);
		*/
		
	}
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#detachAgentListener(java.lang.String, eis.AgentListener)
	 */
	public void detachAgentListener(Agent agent, AgentConnectionHandler handler) 
	{

		if( registeredAgents.contains(agent) == false )
			return;
		
		agent.removeListener(handler);
		
		/*
		HashSet<AgentHandler> listeners = this.agentsToAgentListeners.get(agent);
		
		if( listeners == null || listeners.contains(listener) == false )
			return;
		
		listeners.remove(listener);
		
		agentsToAgentListeners.put(agent,listeners);
	*/
	}
	
	public void attachEntityListener(Entity entity, EntityConnectionHandler entityHandler)
	{
		
		if ( !this.entities.contains(entity) && !this.freeEntities.contains(entity))
			return;
		
		entity.addListener(entityHandler);
		
		
		
	}
	
	public void detachEntityListener(Entity entity, EntityConnectionHandler entityHandler)
	{
		if ( !this.entities.contains(entity) && !this.freeEntities.contains(entity))
			return;
		
		entity.removeHandler(entityHandler);
	}
	
	/**
	 * Notifies agents about a percept.
	 * 
	 * @param percept is the percept
	 * @param agents is the array of agents that are to be notified about the event.
	 * If the array is empty, all registered agents will be notified. The array has to 
	 * contain only registered agents.
	 * @throws AgentException is thrown if at least one of the agents in the array is not
	 * registered.
	 */
     protected void notifyAgents(DataContainer percept, Agent...agents) throws EnvironmentInterfaceException 
     {

		// no listeners, no notification
		// BUG
		//if (agentsListeners.isEmpty())
			//return;

		// send to all registered agents
   	  /*
		if (agents == null) {

			for (Agent agent : registeredAgents) {
				
				HashSet<AgentHandler> agentListeners = agentsToAgentListeners.get(agent);

				if( agentListeners == null )
					continue;
				
				for (AgentHandler listener : agentListeners) {

					listener.handlePercept(agent, percept);

				}

			}

			return;
		}
		*/
		// send to specified agents
		for (Agent agent : agents) 
		{

			if (!registeredAgents.contains(agent))
			{
				throw new EnvironmentInterfaceException("Agent " + agent
						+ " has not been registered to the environment.");
			}
			
			HashSet<AgentConnectionHandler> agentHandlers = agent.getListeners();

			if( agentHandlers == null )
				continue;
			
			for (AgentConnectionHandler listener : agentHandlers) 
			{
				listener.handleMessage(percept.getSource());
			}

		}

	}
	

	
	
	
	/**
	 * Notifies all listeners about an entity that is free.
	 * 
	 * @param en is the free entity.
	 */
	protected void notifyFreeEntity(Entity en) 
	{
		
		for ( EnvironmentListener listener : environmentListeners ) 
		{
			
			listener.handleFreeEntity(en);
			
		}
		
	}

	
	
	/**
	 * Notifies all listeners about an entity that has been newly created.
	 * 
	 * @param entity is the new entity.
	 */
	protected void notifyNewEntity(Entity entity) 
	{
		
		for( EnvironmentListener listener : environmentListeners ) 
		{
			
			listener.handleNewEntity(entity);
			
		}
		
	}

	
	
	/**
	 * Notifies all listeners about an entity that has been deleted.
	 * 
	 * @param entity is the deleted entity.
	 */
	protected void notifyDeletedEntity(String entity) 
	{
		
		for ( EnvironmentListener listener : environmentListeners ) 
		{
			
			listener.handleDeletedEntity(entity);
			
		}
		
	}

	
	
	
	/**
	 * Notifies the listeners about an environment-event.
	 * 
	 * @param event
	 */
	protected void notifyEnvironmentEvent(EnvironmentEvent event) 
	{
		
		for( EnvironmentListener listener : environmentListeners ) 
		{
			
			listener.handleEnvironmentEvent(event);
			
		}
		
	}

	

	/*
	 * Registering functionality. Registering and unregistering agents.
	 */

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#registerAgent(java.lang.String)
	 */
	public void registerAgent(Agent agent) throws AgentException {

		if (registeredAgents.contains(agent))
			throw new AgentException("Agent " + agent.getName()
					+ " has already registered to the environment.");

		registeredAgents.add(agent);

	}

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#unregisterAgent(java.lang.String)
	 */
	public void unregisterAgent(Agent agent) throws AgentException {

		// fail if agents is not registered
		if (!registeredAgents.contains(agent))
			throw new AgentException("Agent " + agent.getName()
					+ " has not been registered to the environment.");

		// remove from mapping, might be null
		agentsToEntities.remove(agent);
		
		// remove listeners
		// agentsToAgentListeners.remove(agent);
		
		// finally remove from registered list
		registeredAgents.remove(agent);

	}

	
	
	/*
	 * Entity functionality. Adding and removing entities.
	 */
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#getAgents()
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Agent> getAgents() 
	{
		
		return (LinkedList<Agent>)registeredAgents.clone();
		
	}

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#getEntities()
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Entity> getEntities()
	{
		
		return (LinkedList<Entity>) entities.clone();
		
	}


	
	/*
	 * Agents-entity-relation manipulation functionality.
	 */

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#associateEntity(java.lang.String, java.lang.String)
	 */
	public void associateEntity(Agent agent, Entity entity) throws RelationException {
		
		// check if exists
		if( !entities.contains(entity) )
			throw new RelationException("Entity \"" + entity.getIdentifier() + "\" does not exist!!");

		if( !registeredAgents.contains(agent) )
			throw new RelationException("Agent \"" + agent.getName() + "\" has not been registered!");

		// check if associated
		if( !freeEntities.contains(entity) )
			throw new RelationException("Entity \"" + entity.getIdentifier() + "\" has already been associated!");
	
		// remove
		freeEntities.remove(entity);
		
		// associate
		HashSet<Entity> ens = agentsToEntities.get(agent);
		if( ens == null ) {
			
			ens = new HashSet<Entity>();
		}
		ens.add(entity);
		agentsToEntities.put(agent, ens);
		
	}

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#freeEntity(java.lang.String)
	 */
	public void freeEntity(Entity entity) throws RelationException {

		// check if exists
		if( !entities.contains(entity) )
			throw new RelationException("Entity \"" + entity + "\" does not exist!");

		// find the association and remove
		boolean associated = false;
		for( Entry<Agent,HashSet<Entity>> entry : agentsToEntities.entrySet()) {
			
			Agent agent = entry.getKey();
			HashSet<Entity> ens = entry.getValue();
			
			if( ens.contains(entity) ) {
				
				ens.remove(entity);
				
				agentsToEntities.put(agent, ens);
				
				associated = true;
				
				break;
			}
			
		}
		
		// fail if entity has not been associated
		if( associated == false)
			throw new RelationException("Entity \"" + entity + "\" has not been associated!");
	
		// add to free entites
		freeEntities.add(entity);
		
		// notify
		notifyFreeEntity(entity);
		
	}
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#freeAgent(java.lang.String)
	 */
	public void freeAgent(String agent) throws RelationException {
		
		// check if exists
		if( !registeredAgents.contains(agent) )
			throw new RelationException("Agent \"" + agent + "\" does not exist!");
		
		HashSet<Entity> ens = agentsToEntities.get(agent);
	
		this.freeEntities.addAll(ens);

		// notify
		for( Entity en : ens )
			notifyFreeEntity(en);

		agentsToEntities.remove(agent);
		
		
	}

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#freePair(java.lang.String, java.lang.String)
	 */
	public void freePair(Agent agent, Entity entity) throws RelationException 
	{

		// check if exists
		if( !registeredAgents.contains(agent) )
			throw new RelationException("Agent \"" + agent.getName() + "\" does not exist!");

		// check if exists
		if( !entities.contains(entity) )
			throw new RelationException("Entity \"" + entity + "\" does not exist!");
	
		HashSet<Entity> ens = agentsToEntities.get(agent);
		
		if ( ens == null || ens.contains(entity) == false)
			throw new RelationException("Agent \"" + agent.getName() + " is not associated with entity \"" + entity + "\"!");

		// update mapping
		ens.remove(entity);
		agentsToEntities.put(agent,ens);
		
		// store as free entity
		this.freeEntities.add(entity);

		// notify
		for( Entity en : ens )
			notifyFreeEntity(en);
	
	}
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#getAssociatedEntities(java.lang.String)
	 */
	public HashSet<Entity> getAssociatedEntities(Agent agent) throws AgentException {
		
		if( registeredAgents.contains(agent) == false )
			throw new AgentException("Agent \"" + agent.getName() + "\" has not been registered.");
		
		HashSet<Entity> ret = this.agentsToEntities.get(agent);
		
		if( ret == null)
			ret = new HashSet<Entity>();
		
		return ret;
		
	}

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#getAssociatedAgents(java.lang.String)
	 */
	public HashSet<Agent> getAssociatedAgents(String entity) throws EntityException 
	{
		
		if( entities.contains(entity) == false )
			throw new EntityException("Entity \"" + entity + "\" has not been registered.");
		
		HashSet<Agent> ret = new HashSet<Agent>();
		
		for( Entry<Agent, HashSet<Entity>> entry : agentsToEntities.entrySet() ) {
			
			if( entry.getValue().contains(entity) )
				ret.add(entry.getKey());
			
		}
		
		return ret;
		
	}

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#getFreeEntities()
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Entity> getFreeEntities() {
		
		return (LinkedList<Entity>) freeEntities.clone();
		
	}

	
	/*
	 * Acting/perceiving functionality.
	 */

	
	// TODO use freeAgent here
	// TODO maybe use isConnencted here
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#performAction(java.lang.String, eis.iilang.Action, java.lang.String[])
	 */
	public LinkedList<Percept> performAction(String agent, Action action, Entity...entities)
	throws ActException, NoEnvironmentException {

		// unregistered agents cannot act
		if( registeredAgents.contains(agent) == false )
			throw new ActException( ActException.NOTREGISTERED );
		
		// get the associated entities
		HashSet<Entity> associatedEntities = agentsToEntities.get(agent);
		
		// no associated entity/ies -> trivial reject
		if( associatedEntities == null || associatedEntities.size() == 0 )
			throw new ActException( ActException.NOENTITIES );

		// entities that should perform the action
		HashSet<String> targetEntities = null;
		if( entities.length == 0 )
		{
			/*
			 * Convert from HashSet<Entity> to HashSet<String> 
			 * the Identifiers.
			 */
			HashSet<String> tempSet = new HashSet<String>();
			for ( Entity entity : associatedEntities )
			{
				tempSet.add(entity.getIdentifier());
			}
			targetEntities = tempSet;
		
		}
		else {
			
			// targetEntities needs to be HashSet<String> because it is going to
			// be the list of entity to notify in the Environment.
			targetEntities = new HashSet<String>();
			
			for( Entity entity : entities ) {
				
				if( associatedEntities.contains(entity) == false)
					throw new ActException( ActException.WRONGENTITY );
			
				targetEntities.add(entity.getIdentifier());
				
			} 
			
		}
		
		// get the parameters
		LinkedList<Parameter> params = action.getParameters();
		
		// targetEntities contains all entities that should perform the action
		// params contains all parameters

		// determine class parameters for finding the method
		// and store the parameters as objects
		Class<?>[] classParams = new Class[params.size()+1];
		classParams[0] = String.class; // entity name
		for( int a = 0 ; a < params.size() ; a++ )
			classParams[a+1] = params.get(a).getClass();

		//for( Class<?> p : classParams )
		  //System.out.println(p);
		
		// return value
		LinkedList<Percept> rets = new LinkedList<Percept>();
		
		try {

			// lookup the method
			String methodName = "action" + action.getName();
			//System.out.println(methodName);
			
			Method m = this.getClass().getMethod(methodName,classParams);

			if( Class.forName("eis.iilang.Percept").isAssignableFrom(m.getReturnType()) == false)
				throw new ActException( ActException.WRONGSYNTAX, "The return-type is wrong" );

			//System.out.println("Method: " + m);
			
			// make accessible; hope this is not a bug
			m.setAccessible(true);
			
			// invoke
			for( String entity : targetEntities ) {

				Object[] objParams = new Object[params.size()+1];
				objParams[0] = entity; // entity name
				for( int a = 0 ; a < params.size() ; a++ )
					objParams[a+1] = params.get(a);
				
				Percept ret = (Percept) m.invoke(this, objParams );
				
				if( ret != null)
				  rets.add( ret );
				
			}

		} catch (ClassNotFoundException e) {

			throw new ActException( ActException.WRONGSYNTAX, "Class not found", e);
			
		} catch (SecurityException e) {

			throw new ActException(ActException.WRONGSYNTAX, "Security exception", e);

		} catch (NoSuchMethodException e) {

			throw new ActException(ActException.WRONGSYNTAX, "No such method", e);
			
		} catch (IllegalArgumentException e) {

			throw new ActException(ActException.WRONGSYNTAX, "Illegal argument", e);
		
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());

			throw new ActException(ActException.WRONGSYNTAX, "Illegal access", e);

		} catch (InvocationTargetException e) {

			// action has failed -> let fail
			if(e.getCause() instanceof ActException )
				throw (ActException) e.getCause(); // rethrow
			else if(e.getCause() instanceof NoEnvironmentException)
				throw (NoEnvironmentException) e.getCause(); // rethrow
	
			throw new ActException(ActException.WRONGSYNTAX, "Invocation target exception", e);
		
		}
		
		return rets;

	}
	
	// TODO maybe use isConnencted here
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#getAllPercepts(java.lang.String, java.lang.String[])
	 */
	
	
	/**
	 * Gets all percepts of an entity.
	 * <p/>
	 * This method must be overridden.
	 * 
	 * @param entity is the entity whose percepts should be retrieved.
	 * @return a list of percepts.
	 */
	protected abstract LinkedList<Percept> getAllPerceptsFromEntity(Entity entity) throws PerceiveException, NoEnvironmentException;

	
	
	/*
	 * Management functionality.
	 */
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#manageEnvironment(eis.iilang.EnvironmentCommand, java.lang.String[])
	 */
	public abstract void manageEnvironment(EnvironmentCommand command) 
	throws ManagementException,NoEnvironmentException;

	/*
	 * Misc functionality.
	 */
	

	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#release()
	 */
	public abstract void release();
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#isConnected()
	 */
	public abstract boolean isConnected();
	
	/* (non-Javadoc)
	 * @see eis.EnvironmentInterfaceStandard#getType(java.lang.String)
	 */
	public String getType(Entity entity) throws EntityException
	{
		
		if( !this.entities.contains(entity) )
			throw new EntityException("Entity \"" + entity.getIdentifier() + "\" does not exist!");
		
		String type = entitiesToTypes.get(entity);
		
		if( type == null )
			type = "unknown";
			
		return type;
		
	}

	/** 
	 * Adds an entity to the environment.
	 * 
	 * @param entity is the identifier of the entity that is to be added.
	 * @throws PlatformException is thrown if the entity already exists.
	 */
	protected void addEntity(Entity entity) throws EntityException {

		// fail if entity does exist
		if( entities.contains(entity) )
			throw new EntityException("Entity \"" + entity + "\" does already exist");
		
		// add
		entities.add(entity);
		freeEntities.add(entity);
		
		// notify
		notifyNewEntity(entity);
		
	}

	/** 
	 * Adds an entity to the environment.
	 * 
	 * @param entity is the identifier of the entity that is to be added.
	 * @param type is the type of the entity.
	 * @throws PlatformException is thrown if the entity already exists.
	 */
	protected void addEntity(Entity entity,String type) throws EntityException {

		// fail if entity does exist
		if( entities.contains(entity) )
			throw new EntityException("Entity \"" + entity + "\" does already exist");
		
		// add
		entities.add(entity);
		freeEntities.add(entity);
		
		// set type
		this.setType(entity, type);
		
		// notify
		notifyNewEntity(entity);
		
	}
	
	
	/**
	 * Deletes an entity, by removing its id from the internal list, and disassociating 
	 * it from the respective agent.
	 * 
	 * @param entity the id of the entity that is to be removed.
	 * @throws PlatformException if the agent does not exist.
	 */
	// TODO use freeEntity here
	protected void deleteEntity(String entity) throws EntityException {
	
		// fail if entity does not exist
		if( !entities.contains(entity) )
			throw new EntityException("Entity \"" + entity + "\" does not exist");

		// find the association and remove
		for( Entry<Agent,HashSet<Entity>> entry : agentsToEntities.entrySet()) {
			
			Agent agent = entry.getKey();
			HashSet<Entity> ens = entry.getValue();
			
			if( ens.contains(entity) ) {
				
				ens.remove(entity);
				
				agentsToEntities.put(agent, ens);
				
				break;
			}
			
		}

		// finally delete
		entities.remove(entity);
		freeEntities.remove(entity);
		if(this.entitiesToTypes.containsKey(entity))
			this.entitiesToTypes.remove(entity);
		
		// notify 
		notifyDeletedEntity(entity);
		
	}

	/**
	 * Sets the type of an entity.
	 * 
	 * @param entity is the entity
	 * @param type is the respective type of the entity
	 * @throws EntityException is thrown if the entity doas nox exist or if it already has a type.
	 */
	public void setType(Entity entity, String type) throws EntityException {

		if( !entities.contains(entity) )
			throw new EntityException("Entity \"" + entity + "\" does not exist!");
		
		if( entitiesToTypes.get(entity) != null )
			throw new EntityException("Entity \"" + entity + "\" already has a type!");
	
		entitiesToTypes.put(entity, type);

	}



	public void freeAgent(Agent agent) throws RelationException
	{

		if ( !this.registeredAgents.contains(agent) )
		{
			throw new RelationException("The agent " + agent.getName() + " is not registered");
		}
		
		
		this.registeredAgents.remove(agent);
		
	}

	public LinkedList<Percept> getAllPercepts(Agent agent, Entity... entities)
			throws PerceiveException, NoEnvironmentException
	{
		// TODO Auto-generated method stub
		return null;
	}



	public HashSet<Agent> getAssociatedAgents(Entity entity)
			throws EntityException
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String requiredVersion()
	{
		// TODO Auto-generated method stub
		return "0.3";
	}

}