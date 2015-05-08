package com.testsys.web.bean;

import com.testsys.constants.CommonConstant.paperStatus;

public class PaperState {

	public static boolean stateTurnCheck(paperStatus begin,paperStatus end)
	{
		boolean result = false;
		switch(begin)
		{
			case SIGNIN:
				if(end == paperStatus.HASPAY || end == paperStatus.CHOICEEXPIRED)
					result = true;
				break;
			case HASPAY:
				if(end == paperStatus.CHOICEING || end == paperStatus.CHOICEEXPIRED)
					result = true;
				break;
			case CHOICEING:
				if(end == paperStatus.CHOICENOTPASS ||end == paperStatus.CHOICEPASS || end == paperStatus.CHOICEEXPIRED)
					result = true;
				break;
			case CHOICEPASS:
				if(end == paperStatus.OPENBEGIN || end == paperStatus.OPENEXPIRED)
					result = true;
				break;
			case OPENBEGIN:
				if(end == paperStatus.OPENCHOOSE || end == paperStatus.OPENEXPIRED)
					result = true;
				break;
			case OPENCHOOSE:
				if(end == paperStatus.OPENSUBMIT || end == paperStatus.OPENEXPIRED)
					result = true;
				break;
			case OPENSUBMIT:
				if(end == paperStatus.OPENREVIEWED)
					result = true;
				break;
		}
		return result;
	}
}
