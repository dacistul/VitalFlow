	switch (t->back) {
	default: Uerror("bad return move");
	case  0: goto R999; /* nothing to undo */

		 /* CLAIM safety_check */
;
		;
		;
		;
		
	case 5: // STATE 10
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC Pump */

	case 6: // STATE 1
		;
		XX = 1;
		unrecv(now.pump_cmd, XX-1, 0, ((int)((P2 *)_this)->trigger), 1);
		((P2 *)_this)->trigger = trpt->bup.oval;
		;
		;
		goto R999;

	case 7: // STATE 2
		;
	/* 0 */	((P2 *)_this)->trigger = trpt->bup.oval;
		;
		;
		goto R999;

	case 8: // STATE 3
		;
		now.pump_active = trpt->bup.oval;
		;
		goto R999;

	case 9: // STATE 4
		;
		now.insulin_reservoir = trpt->bup.oval;
		;
		goto R999;

	case 10: // STATE 5
		;
		now.glucose_level = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 12: // STATE 8
		;
	/* 0 */	((P2 *)_this)->trigger = trpt->bup.oval;
		;
		;
		goto R999;

	case 13: // STATE 9
		;
		alarm_triggered = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 15: // STATE 11
		;
	/* 0 */	((P2 *)_this)->trigger = trpt->bup.oval;
		;
		;
		goto R999;

	case 16: // STATE 12
		;
		now.pump_active = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 18: // STATE 19
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC Controller */

	case 19: // STATE 1
		;
		XX = 1;
		unrecv(now.sensor_data, XX-1, 0, ((P1 *)_this)->current_level, 1);
		((P1 *)_this)->current_level = trpt->bup.oval;
		;
		;
		goto R999;

	case 20: // STATE 2
		;
	/* 0 */	((P1 *)_this)->current_level = trpt->bup.oval;
		;
		;
		goto R999;

	case 21: // STATE 3
		;
		_m = unsend(now.pump_cmd);
		;
		goto R999;

	case 22: // STATE 4
		;
	/* 0 */	((P1 *)_this)->current_level = trpt->bup.oval;
		;
		;
		goto R999;

	case 23: // STATE 5
		;
		_m = unsend(now.pump_cmd);
		;
		goto R999;

	case 24: // STATE 7
		;
		_m = unsend(now.pump_cmd);
		;
		goto R999;

	case 25: // STATE 13
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC Sensor */
;
		;
		
	case 27: // STATE 3
		;
		now.glucose_level = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 29: // STATE 5
		;
		now.glucose_level = trpt->bup.oval;
		;
		goto R999;

	case 30: // STATE 10
		;
		_m = unsend(now.sensor_data);
		;
		goto R999;

	case 31: // STATE 14
		;
		p_restor(II);
		;
		;
		goto R999;
	}

