Eight Signals robot by Batandwa Mgutsi

bot type: 1v1

This is one of those instances where I start big, only to be hit by a problem that will
require more time than i have to work around, and so the only choice was to delete
everything and just keep it simple. The end result was Eight Signals, a simpe bot that
exploits the fact that it is hard for other bots to hit you while you are moving.

The bot is implemented in one Source File, included in the submission, therefore there
is not much architecturing into it.

Not Dying:

Eight Signals moves from corner to corner, and never stops. This makes it really hard to
shoot at. Even "Tracker" could not destroy it.

Targeting:
When Eight Signals gets to a corner, it makes a 360 degree scan of the environment.
It fires at any other bot it finds in this scan, and moves on to the next corner.
To maximise points, Eight Signals also fires at other bots while it is moving, if it
happens that those bots are in the direction its gun is pointing at.

Max Power
When a bot is less than 3 bot width units away from Eight Signals, Eight Signals will
fire at the bot using the maximum power a single bullet can have. This is because, in
this range the other bot will be close enough that missing is nearly impossible.
therefore, the max power is hardly wasted. The "Crazy" example bot is hit hard by these
max powers.

Personal Experience
I spent too much time trying to design the most sophisticated bot I could think of.
When I had 3 hours left, I realised one of the algorithms I needed would take some time
to get right and would require a lot of unit testing. Therefore I compromised, deleted
everything and went for a simpler bot instead.

I have to say though, Once my bot started playing, it got really fun. Installation
of the environment was straight forward.