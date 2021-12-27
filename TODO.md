#TODO
TODO-List for RaPiTelegram-Project

- [ ] Wrap Commands in Handler.class in Classes for each command
   - [ ] automatic detection of commands in specific package?/folder? -> plugin-like abilities
- [ ] Wrap Privileges in AccountPrivileges.class in Classes for each privilege
   - [ ] Superclass / subclass-relation
- [ ] List privileges of specific user as extension to listPrivileges-command by passing parameter
- [ ] Give Permissions by Permission Name. Get ID of permission by checking getPrivilegeID(String)-method
- [ ] Command not found-Message
- [ ] Interactive Commands

##Known bugs
- [ ] switchOn and switchOff commands don't work, switch-command does.
- [x] crashes when postpone-command is used with switch-command
