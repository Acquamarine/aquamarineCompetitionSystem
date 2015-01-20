<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Multi-gaming competition system</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css">
        <link href="/MultigamingCompetitionSystem/css/userProfile.css" rel="stylesheet" type="text/css">
        <link href="/MultigamingCompetitionSystem/css/teams.css" rel="stylesheet" type="text/css">
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
        <div class="UserInvitationsContainer">
            <c:if test="${!myInvitations.isEmpty()}">
                <div class="PlatformGamesTitle">Pending Invitations</div>
                <div class="FrontBanner">
                    <ul class="Members">
                        <c:forEach items="${myInvitations}" var="myInvitation">
                            <li class="Member PendingMember Inline">
                                <div class="ProfileIcon Inline">
                                    <div class="CompetitorImage MemberImage Inline">
                                        <div class="borderImage">
                                            <img src="">
                                        </div>
                                        <div class="CompetitorAvatar">
                                            <img class="CompetitorAvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="Inline">
                                    <div class="CompetitorName MemberName">
                                        <a class="MemberLink" href="/MultigamingCompetitionSystem/userProfile?user=${myInvitation.getInvitingTeam().getNickname()}">
                                            ${myInvitation.getInvitingTeam().getNickname()} 
                                        </a>
                                    </div>
                                    <ul class="AcceptOrDecline">
                                        <li class="Accept Inline">
                                            <form action="/MultigamingCompetitionSystem/teams?accept=${myInvitation.getInvitingTeam().getNickname()}" method="post">
                                                <input class="Submit ADButton GreenButton" type="submit" value="Accept"/>
                                            </form>
                                        </li>
                                        <li class="Decline Inline">
                                            <form action="/MultigamingCompetitionSystem/teams?decline=${myInvitation.getInvitingTeam().getNickname()}" method="post">
                                                <input class="Submit ADButton RedButton" type="submit" value="Decline"/>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </c:forEach> 
                    </ul>
                </div>
            </c:if>
        </div>
        <div class="AllTeams">
			<div class="AllTeamsHeader">
				<div class="PlatformGamesTitle">Teams</div>
				<div class="InvitationsSearchBoxContainer CreateTeam">
					<form action="/MultigamingCompetitionSystem/teams" method="get">
						<div class="InvitationsSearchBox">
							<div class="HeaderSearchBoxBlock Inline">
								<div class="HeaderSearchBoxInput Inline">
									<input type="text" name="createTeam" class="Search NoSpace"  placeholder="Team Name">
								</div>
								<div class="HeaderSearchButton Inline">
									<input class="Submit Invite" type="submit"  value="Create Team">
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
            <div class="TeamsList">
                <c:forEach items="${usersTeams}" var="team">
                    <div class="TeamDiv FrontBanner">
                        <div class="IconAndName">
                            <div class="ProfileIcon Inline">
                                <div class="CompetitorImage Inline">
                                    <div class="borderImage">
                                        <img src="">
                                    </div>
                                    <div class="CompetitorAvatar">
                                        <img class="CompetitorAvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                                    </div>
                                </div>
                            </div>
                            <div class="CompetitorName Inline">
                                <div class="Inline">
                                    ${team.getNickname()} 
                                </div>
                            </div>
                        </div>
                        <div class="PlatformGamesTitle MembersTitle">Members</div>
                        <ul class="Members">
                            <c:forEach items="${team.getMembers()}" var="member">
                                <li class="Member Inline">
                                    <div class="ProfileIcon Inline">
                                        <div class="CompetitorImage MemberImage Inline">
                                            <div class="borderImage">
                                                <img src="">
                                            </div>
                                            <div class="CompetitorAvatar">
                                                <img class="CompetitorAvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="CompetitorName MemberName Inline">
                                        <a class="MemberLink Inline" href="/MultigamingCompetitionSystem/userProfile?user=${member.getNickname()}">
                                            ${member.getNickname()} 
                                        </a>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                        <div class="Invitations">
                            <div class="InvitationsHeader">
                                <c:if test="${!invitationsMap.get(team.getNickname()).isEmpty()}">
                                    <div class="PlatformGamesTitle InvitationsTitle Inline">Pending Invitations</div>
                                </c:if>
                                <div class="InvitationsSearchBoxContainer Inline">
                                    <form action="/MultigamingCompetitionSystem/teams" method="GET">
                                        <input type="hidden" name="team" value="${team.getNickname()}" /> 
                                        <div class="InvitationsSearchBox">
                                            <div class="HeaderSearchBoxBlock Inline">
                                                <div class="HeaderSearchBoxInput Inline">
                                                    <input type="text" name="invitedUser" class="Search"  placeholder="User Nickname">
                                                </div>
                                                <div class="HeaderSearchButton Inline">
                                                    <input class="Submit Invite" type="submit"  value="Invite user">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <ul class="Members">
                                <c:forEach items="${invitationsMap.get(team.getNickname())}" var="teamInvitation">
                                    <li class="Member PendingMember Inline">
                                        <div class="ProfileIcon Inline">
                                            <div class="CompetitorImage MemberImage Inline">
                                                <div class="borderImage">
                                                    <img src="">
                                                </div>
                                                <div class="CompetitorAvatar">
                                                    <img class="CompetitorAvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="CompetitorName MemberName Inline">
                                            <a class="MemberLink Inline" href="/MultigamingCompetitionSystem/userProfile?user=${teamInvitation.getInvitedUser().getNickname()}">
                                                ${teamInvitation.getInvitedUser().getNickname()} 
                                            </a>
                                        </div>
                                    </li>
                                </c:forEach> 
                            </ul>
                        </div>  


                    </div>
                </c:forEach>

            </div>

        </div>

        <%@include file="../../resources/html/footer.html" %>
    </body>
</html>
