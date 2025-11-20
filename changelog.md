------------------------------------------------------
Ratatouille 1.4.1 - 1.21.1
------------------------------------------------------
- Fixed cosmetics screen being blurry and player preview being wrong
- Fixed a disconnect that would happen when changing plush cosmetics on a server

------------------------------------------------------
Ratatouille 1.4 - 1.21.1
------------------------------------------------------
- Added a registrar util to streamline blocks, items, entity types, sound events, etc... registering
- Added an option locker util
- Added text utils
- Fixed plushies using a hand item renderer instead of a simple item renderer

------------------------------------------------------
Ratatouille 1.3 - 1.21.1
------------------------------------------------------
- Added an ambience util: can be used to register background ambiences or block entity dependant ambiences

------------------------------------------------------
Ratatouille 1.2.5 - 1.21.1
------------------------------------------------------
- Custom armor pieces using the custom model armor util now properly display glint
- Added four new methods to the ArmorDisplayConditions class that allows defining custom glint display conditions for armor pieces
- Added a custom test armor using the custom model armor util in dev environments

------------------------------------------------------
Ratatouille 1.2.4 - 1.21.1
------------------------------------------------------
- Fixed custom armor rendering missing textures and vanilla armor no longer rendering (I accidentally inverted the check condition I'm so silly and incompetent)

------------------------------------------------------
Ratatouille 1.2.3 - 1.21.1
------------------------------------------------------
- Removed the CustomRenderArmor interface and CustomRenderArmorItem class and made the CustomModelArmorUtil automatically not render items registered with ItemSetDisplayConditions

------------------------------------------------------
Ratatouille 1.2.2 - 1.21.1
------------------------------------------------------
- Replaced CustomModelArmorUtil.SetItems record with the ArmorDisplayConditions abstract class
  - This abstract class has no functionality on its own and needs to be extended and have its four shouldDisplay methods defined
- Added an ItemSetDisplayConditions class that works the same way CustomModelArmorUtil.SetItems did

------------------------------------------------------
Ratatouille 1.2.1 - 1.21.1
------------------------------------------------------
- Removed getRightArm and getLeftArm from the RendersArmInFirstPerson interface
  - Given the interface is supposed to be implemented on a BipedEntityModel, it will now automatically use the "right_arm" and "left_arm" parts of the biped model
- Tweaked the custom armor model and template to more closely match biped entity models
- Fixed custom armor models not properly matching baby biped mobs

------------------------------------------------------
Ratatouille 1.2.0 - 1.21.1
------------------------------------------------------
- Added the ability to give items and armor to mobs by interacting with them while in creative and sneaking with an item in hand
- Added the ability to hit mobs in creative while sneaking with an empty hand to make them drop all their equipped items
- Added a custom model armor util
  - Allows adding armor with custom models with a single method call (and custom model)
  - Automatically displays said custom armor on a player's hands in first person.

------------------------------------------------------
Ratatouille 1.1.3 - 1.21.1
------------------------------------------------------
- Changed getRightArm for the RendersArmInFirstPerson interface to take in a living entity parameter
- Changed getLeftArm for the RendersArmInFirstPerson interface to take in a living entity parameter

------------------------------------------------------
Ratatouille 1.1.2 - 1.21.1
------------------------------------------------------
- Changed getTexture for the RendersArmInFirstPerson interface to take in a living entity parameter

------------------------------------------------------
Ratatouille 1.1.1 - 1.21.1
------------------------------------------------------
- Added an interface and class for custom armor items that does not render the vanilla armor model when equipped
- Changed getModel for the RendersArmInFirstPerson interface to take in a living entity parameter

------------------------------------------------------
Ratatouille 1.1.0 - 1.21.1
------------------------------------------------------
- Added an interface that allows feature renderers extending it to render on the arm in first person automatically

------------------------------------------------------
Ratatouille 1.0.8 - 1.20.1
------------------------------------------------------
- Fixed custom hit particle and sound items not always playing their effects when crits do

------------------------------------------------------
Ratatouille 1.0.8 - 1.20.1
------------------------------------------------------
- Added item custom hit sound and particle utils

------------------------------------------------------
Ratatouille 1.0.7 - 1.20.1
------------------------------------------------------
- Added a subtitle for the plush honk sounds

------------------------------------------------------
Ratatouille 1.0.6 - 1.20.1
------------------------------------------------------
- Removed colored names from supporters for now, leaving only a folly red rat icon appended to the name
  - Plan is to make supporters able to choose their own custom colors in the future
- Fixed supporter icons appearing for non-supporters
- Fixed supporter cosmetics screen not appearing locked to non-supporters

------------------------------------------------------
Ratatouille 1.0.5 - 1.20.1
------------------------------------------------------
- Removed Cardinal Components, Satin API and Midnightlib from dependencies

------------------------------------------------------
Ratatouille 1.0.4 - 1.20.1
------------------------------------------------------
- Replaced supporter titles in chat and player list with a rat icon
- Removed player skulls
  - These will be released as part of a separate bigger gameplay tweaks mod in the future

------------------------------------------------------
Ratatouille 1.0.3 - 1.20.1
------------------------------------------------------
- Fixed a desync between equipped cosmetics and indicated cosmetics when opening the plush cosmetics screen

------------------------------------------------------
Ratatouille 1.0.2 - 1.20.1
------------------------------------------------------
- Added a cosmetics configuration screen util
- Added a text formatting util that capitalizes strings and replaces underscores with spaces
- Added a screen to adjust the plush on head cosmetic for supporters, accessible by sneak-right-clicking any plush in the air
- Fixed plush on head rendering rotating too much other feature renderers

------------------------------------------------------
Ratatouille 1.0.1 - 1.20.1
------------------------------------------------------
- Fixed plush on head rendering messing up other feature renderers

------------------------------------------------------
Ratatouille 1.0.0 - 1.20.1
------------------------------------------------------
Initial release