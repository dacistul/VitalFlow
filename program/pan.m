#define rand	pan_rand
#define pthread_equal(a,b)	((a)==(b))
#if defined(HAS_CODE) && defined(VERBOSE)
	#ifdef BFS_PAR
		bfs_printf("Pr: %d Tr: %d\n", II, t->forw);
	#else
		cpu_printf("Pr: %d Tr: %d\n", II, t->forw);
	#endif
#endif
	switch (t->forw) {
	default: Uerror("bad forward move");
	case 0:	/* if without executable clauses */
		continue;
	case 1: /* generic 'goto' or 'skip' */
		IfNotBlocked
		_m = 3; goto P999;
	case 2: /* generic 'else' */
		IfNotBlocked
		if (trpt->o_pm&1) continue;
		_m = 3; goto P999;

		 /* CLAIM safety_check */
	case 3: // STATE 1 - _spin_nvr.tmp:3 - [(!(!((pump_active&&(glucose_level<70)))))] (0:0:0 - 0)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported1 = 0;
			if (verbose && !reported1)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported1 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported1 = 0;
			if (verbose && !reported1)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported1 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[3][1] = 1;
		if (!( !( !((((int)now.pump_active)&&(now.glucose_level<70))))))
			continue;
		_m = 3; goto P999; /* 0 */
	case 4: // STATE 2 - _spin_nvr.tmp:3 - [assert(!(!(!((pump_active&&(glucose_level<70))))))] (0:0:0 - 0)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported2 = 0;
			if (verbose && !reported2)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported2 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported2 = 0;
			if (verbose && !reported2)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported2 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[3][2] = 1;
		spin_assert( !( !( !((((int)now.pump_active)&&(now.glucose_level<70))))), " !( !( !((pump_active&&(glucose_level<70)))))", II, tt, t);
		_m = 3; goto P999; /* 0 */
	case 5: // STATE 10 - _spin_nvr.tmp:8 - [-end-] (0:0:0 - 0)
		
#if defined(VERI) && !defined(NP)
#if NCLAIMS>1
		{	static int reported10 = 0;
			if (verbose && !reported10)
			{	int nn = (int) ((Pclaim *)pptr(0))->_n;
				printf("depth %ld: Claim %s (%d), state %d (line %d)\n",
					depth, procname[spin_c_typ[nn]], nn, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported10 = 1;
				fflush(stdout);
		}	}
#else
		{	static int reported10 = 0;
			if (verbose && !reported10)
			{	printf("depth %d: Claim, state %d (line %d)\n",
					(int) depth, (int) ((Pclaim *)pptr(0))->_p, src_claim[ (int) ((Pclaim *)pptr(0))->_p ]);
				reported10 = 1;
				fflush(stdout);
		}	}
#endif
#endif
		reached[3][10] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC Pump */
	case 6: // STATE 1 - vitalflow.pml:54 - [pump_cmd?trigger] (0:0:1 - 0)
		reached[2][1] = 1;
		if (boq != now.pump_cmd) continue;
		if (q_len(now.pump_cmd) == 0) continue;

		XX=1;
		(trpt+1)->bup.oval = ((int)((P2 *)_this)->trigger);
		;
		((P2 *)_this)->trigger = qrecv(now.pump_cmd, XX-1, 0, 1);
#ifdef VAR_RANGES
		logval("Pump:trigger", ((int)((P2 *)_this)->trigger));
#endif
		;
		
#ifdef HAS_CODE
		if (readtrail && gui) {
			char simtmp[32];
			sprintf(simvals, "%d?", now.pump_cmd);
		sprintf(simtmp, "%d", ((int)((P2 *)_this)->trigger)); strcat(simvals, simtmp);		}
#endif
		if (q_zero(now.pump_cmd))
		{	boq = -1;
#ifndef NOFAIR
			if (fairness
			&& !(trpt->o_pm&32)
			&& (now._a_t&2)
			&&  now._cnt[now._a_t&1] == II+2)
			{	now._cnt[now._a_t&1] -= 1;
#ifdef VERI
				if (II == 1)
					now._cnt[now._a_t&1] = 1;
#endif
#ifdef DEBUG
			printf("%3ld: proc %d fairness ", depth, II);
			printf("Rule 2: --cnt to %d (%d)\n",
				now._cnt[now._a_t&1], now._a_t);
#endif
				trpt->o_pm |= (32|64);
			}
#endif

		};
		_m = 4; goto P999; /* 0 */
	case 7: // STATE 2 - vitalflow.pml:56 - [((trigger&&(insulin_reservoir>0)))] (0:0:1 - 0)
		IfNotBlocked
		reached[2][2] = 1;
		if (!((((int)((P2 *)_this)->trigger)&&(now.insulin_reservoir>0))))
			continue;
		if (TstOnly) return 1; /* TT */
		/* dead 1: trigger */  (trpt+1)->bup.oval = ((P2 *)_this)->trigger;
#ifdef HAS_CODE
		if (!readtrail)
#endif
			((P2 *)_this)->trigger = 0;
		_m = 3; goto P999; /* 0 */
	case 8: // STATE 3 - vitalflow.pml:58 - [pump_active = 1] (0:0:1 - 0)
		IfNotBlocked
		reached[2][3] = 1;
		(trpt+1)->bup.oval = ((int)now.pump_active);
		now.pump_active = 1;
#ifdef VAR_RANGES
		logval("pump_active", ((int)now.pump_active));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 9: // STATE 4 - vitalflow.pml:59 - [insulin_reservoir = (insulin_reservoir-1)] (0:0:1 - 0)
		IfNotBlocked
		reached[2][4] = 1;
		(trpt+1)->bup.oval = now.insulin_reservoir;
		now.insulin_reservoir = (now.insulin_reservoir-1);
#ifdef VAR_RANGES
		logval("insulin_reservoir", now.insulin_reservoir);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 10: // STATE 5 - vitalflow.pml:60 - [glucose_level = (glucose_level-15)] (0:0:1 - 0)
		IfNotBlocked
		reached[2][5] = 1;
		(trpt+1)->bup.oval = now.glucose_level;
		now.glucose_level = (now.glucose_level-15);
#ifdef VAR_RANGES
		logval("glucose_level", now.glucose_level);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 11: // STATE 6 - vitalflow.pml:61 - [printf('ACTION: Insulin Delivered. Remaining: %d\\n',insulin_reservoir)] (0:0:0 - 0)
		IfNotBlocked
		reached[2][6] = 1;
		Printf("ACTION: Insulin Delivered. Remaining: %d\n", now.insulin_reservoir);
		_m = 3; goto P999; /* 0 */
	case 12: // STATE 8 - vitalflow.pml:63 - [((trigger&&(insulin_reservoir==0)))] (0:0:1 - 0)
		IfNotBlocked
		reached[2][8] = 1;
		if (!((((int)((P2 *)_this)->trigger)&&(now.insulin_reservoir==0))))
			continue;
		if (TstOnly) return 1; /* TT */
		/* dead 1: trigger */  (trpt+1)->bup.oval = ((P2 *)_this)->trigger;
#ifdef HAS_CODE
		if (!readtrail)
#endif
			((P2 *)_this)->trigger = 0;
		_m = 3; goto P999; /* 0 */
	case 13: // STATE 9 - vitalflow.pml:64 - [alarm_triggered = 1] (0:0:1 - 0)
		IfNotBlocked
		reached[2][9] = 1;
		(trpt+1)->bup.oval = ((int)alarm_triggered);
		alarm_triggered = 1;
#ifdef VAR_RANGES
		logval("alarm_triggered", ((int)alarm_triggered));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 14: // STATE 10 - vitalflow.pml:65 - [printf('ALARM: Reservoir Empty!\\n')] (0:0:0 - 0)
		IfNotBlocked
		reached[2][10] = 1;
		Printf("ALARM: Reservoir Empty!\n");
		_m = 3; goto P999; /* 0 */
	case 15: // STATE 11 - vitalflow.pml:66 - [(!(trigger))] (0:0:1 - 0)
		IfNotBlocked
		reached[2][11] = 1;
		if (!( !(((int)((P2 *)_this)->trigger))))
			continue;
		if (TstOnly) return 1; /* TT */
		/* dead 1: trigger */  (trpt+1)->bup.oval = ((P2 *)_this)->trigger;
#ifdef HAS_CODE
		if (!readtrail)
#endif
			((P2 *)_this)->trigger = 0;
		_m = 3; goto P999; /* 0 */
	case 16: // STATE 12 - vitalflow.pml:67 - [pump_active = 0] (0:0:1 - 0)
		IfNotBlocked
		reached[2][12] = 1;
		(trpt+1)->bup.oval = ((int)now.pump_active);
		now.pump_active = 0;
#ifdef VAR_RANGES
		logval("pump_active", ((int)now.pump_active));
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 17: // STATE 13 - vitalflow.pml:68 - [printf('STATUS: Pump Idle. Glucose: %d\\n',glucose_level)] (0:0:0 - 0)
		IfNotBlocked
		reached[2][13] = 1;
		Printf("STATUS: Pump Idle. Glucose: %d\n", now.glucose_level);
		_m = 3; goto P999; /* 0 */
	case 18: // STATE 19 - vitalflow.pml:71 - [-end-] (0:0:0 - 0)
		IfNotBlocked
		reached[2][19] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC Controller */
	case 19: // STATE 1 - vitalflow.pml:34 - [sensor_data?current_level] (0:0:1 - 0)
		reached[1][1] = 1;
		if (boq != now.sensor_data) continue;
		if (q_len(now.sensor_data) == 0) continue;

		XX=1;
		(trpt+1)->bup.oval = ((P1 *)_this)->current_level;
		;
		((P1 *)_this)->current_level = qrecv(now.sensor_data, XX-1, 0, 1);
#ifdef VAR_RANGES
		logval("Controller:current_level", ((P1 *)_this)->current_level);
#endif
		;
		
#ifdef HAS_CODE
		if (readtrail && gui) {
			char simtmp[32];
			sprintf(simvals, "%d?", now.sensor_data);
		sprintf(simtmp, "%d", ((P1 *)_this)->current_level); strcat(simvals, simtmp);		}
#endif
		if (q_zero(now.sensor_data))
		{	boq = -1;
#ifndef NOFAIR
			if (fairness
			&& !(trpt->o_pm&32)
			&& (now._a_t&2)
			&&  now._cnt[now._a_t&1] == II+2)
			{	now._cnt[now._a_t&1] -= 1;
#ifdef VERI
				if (II == 1)
					now._cnt[now._a_t&1] = 1;
#endif
#ifdef DEBUG
			printf("%3ld: proc %d fairness ", depth, II);
			printf("Rule 2: --cnt to %d (%d)\n",
				now._cnt[now._a_t&1], now._a_t);
#endif
				trpt->o_pm |= (32|64);
			}
#endif

		};
		_m = 4; goto P999; /* 0 */
	case 20: // STATE 2 - vitalflow.pml:36 - [((current_level<70))] (0:0:1 - 0)
		IfNotBlocked
		reached[1][2] = 1;
		if (!((((P1 *)_this)->current_level<70)))
			continue;
		if (TstOnly) return 1; /* TT */
		/* dead 1: current_level */  (trpt+1)->bup.oval = ((P1 *)_this)->current_level;
#ifdef HAS_CODE
		if (!readtrail)
#endif
			((P1 *)_this)->current_level = 0;
		_m = 3; goto P999; /* 0 */
	case 21: // STATE 3 - vitalflow.pml:38 - [pump_cmd!0] (0:0:0 - 0)
		IfNotBlocked
		reached[1][3] = 1;
		if (q_len(now.pump_cmd))
			continue;
#ifdef HAS_CODE
		if (readtrail && gui) {
			char simtmp[64];
			sprintf(simvals, "%d!", now.pump_cmd);
		sprintf(simtmp, "%d", 0); strcat(simvals, simtmp);		}
#endif
		
		qsend(now.pump_cmd, 0, 0, 1);
		{ boq = now.pump_cmd; };
		_m = 2; goto P999; /* 0 */
	case 22: // STATE 4 - vitalflow.pml:39 - [((current_level>=200))] (0:0:1 - 0)
		IfNotBlocked
		reached[1][4] = 1;
		if (!((((P1 *)_this)->current_level>=200)))
			continue;
		if (TstOnly) return 1; /* TT */
		/* dead 1: current_level */  (trpt+1)->bup.oval = ((P1 *)_this)->current_level;
#ifdef HAS_CODE
		if (!readtrail)
#endif
			((P1 *)_this)->current_level = 0;
		_m = 3; goto P999; /* 0 */
	case 23: // STATE 5 - vitalflow.pml:41 - [pump_cmd!1] (0:0:0 - 0)
		IfNotBlocked
		reached[1][5] = 1;
		if (q_len(now.pump_cmd))
			continue;
#ifdef HAS_CODE
		if (readtrail && gui) {
			char simtmp[64];
			sprintf(simvals, "%d!", now.pump_cmd);
		sprintf(simtmp, "%d", 1); strcat(simvals, simtmp);		}
#endif
		
		qsend(now.pump_cmd, 0, 1, 1);
		{ boq = now.pump_cmd; };
		_m = 2; goto P999; /* 0 */
	case 24: // STATE 7 - vitalflow.pml:44 - [pump_cmd!0] (0:0:0 - 0)
		IfNotBlocked
		reached[1][7] = 1;
		if (q_len(now.pump_cmd))
			continue;
#ifdef HAS_CODE
		if (readtrail && gui) {
			char simtmp[64];
			sprintf(simvals, "%d!", now.pump_cmd);
		sprintf(simtmp, "%d", 0); strcat(simvals, simtmp);		}
#endif
		
		qsend(now.pump_cmd, 0, 0, 1);
		{ boq = now.pump_cmd; };
		_m = 2; goto P999; /* 0 */
	case 25: // STATE 13 - vitalflow.pml:47 - [-end-] (0:0:0 - 0)
		IfNotBlocked
		reached[1][13] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC Sensor */
	case 26: // STATE 2 - vitalflow.pml:21 - [((glucose_level>40))] (0:0:0 - 0)
		IfNotBlocked
		reached[0][2] = 1;
		if (!((now.glucose_level>40)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 27: // STATE 3 - vitalflow.pml:21 - [glucose_level = (glucose_level-10)] (0:0:1 - 0)
		IfNotBlocked
		reached[0][3] = 1;
		(trpt+1)->bup.oval = now.glucose_level;
		now.glucose_level = (now.glucose_level-10);
#ifdef VAR_RANGES
		logval("glucose_level", now.glucose_level);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 28: // STATE 4 - vitalflow.pml:22 - [((glucose_level<300))] (0:0:0 - 0)
		IfNotBlocked
		reached[0][4] = 1;
		if (!((now.glucose_level<300)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 29: // STATE 5 - vitalflow.pml:22 - [glucose_level = (glucose_level+20)] (0:0:1 - 0)
		IfNotBlocked
		reached[0][5] = 1;
		(trpt+1)->bup.oval = now.glucose_level;
		now.glucose_level = (now.glucose_level+20);
#ifdef VAR_RANGES
		logval("glucose_level", now.glucose_level);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 30: // STATE 10 - vitalflow.pml:25 - [sensor_data!glucose_level] (0:0:0 - 0)
		IfNotBlocked
		reached[0][10] = 1;
		if (q_len(now.sensor_data))
			continue;
#ifdef HAS_CODE
		if (readtrail && gui) {
			char simtmp[64];
			sprintf(simvals, "%d!", now.sensor_data);
		sprintf(simtmp, "%d", now.glucose_level); strcat(simvals, simtmp);		}
#endif
		
		qsend(now.sensor_data, 0, now.glucose_level, 1);
		{ boq = now.sensor_data; };
		_m = 2; goto P999; /* 0 */
	case 31: // STATE 14 - vitalflow.pml:27 - [-end-] (0:0:0 - 0)
		IfNotBlocked
		reached[0][14] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */
	case  _T5:	/* np_ */
		if (!((!(trpt->o_pm&4) && !(trpt->tau&128))))
			continue;
		/* else fall through */
	case  _T2:	/* true */
		_m = 3; goto P999;
#undef rand
	}

