package model.tickbar;

/**
 * the ItemType declares the name, type and behaviour of the TickBarItem
 * currently three types are used:
 * ->Players are the discord users with their discord user entities
 * ->Enemys are simple replicas of players, but with a short description instead of the user object
 * ->Msg are used to set reminders on the tickleiste (f.e damage output, end of battle etc)
 * @author Cornelius
 *
 */
public enum ItemType {
	ENEMY,
	PLAYER,
	MSG,
}
