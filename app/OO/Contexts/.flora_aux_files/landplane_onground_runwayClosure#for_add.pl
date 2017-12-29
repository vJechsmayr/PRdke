
:-(compiler_options([xpp_on,canonical])).

/********** Tabling and Trailer Control Variables ************/

#define EQUALITYnone
#define INHERITANCEflogic
#define TABLINGreactive
#define TABLINGvariant
#define CUSTOMnone

#define FLORA_INCREMENTAL_TABLING 

/************************************************************************
  file: headerinc/flrheader_inc.flh

  Author(s): Guizhen Yang

  This file is automatically included by the Flora-2 compiler.
************************************************************************/

:-(compiler_options([xpp_on])).
#mode standard Prolog

#include "flrheader.flh"
#include "flora_porting.flh"

/***********************************************************************/

/************************************************************************
  file: headerinc/flrheader_prog_inc.flh

  Author(s): Michael Kifer

  This file is automatically included by the Flora-2 compiler.
************************************************************************/

:-(compiler_options([xpp_on])).
#mode standard Prolog

#include "flrheader_prog.flh"

/***********************************************************************/

#define FLORA_COMPILATION_ID 2

#mode save
#mode nocomment "%"
#define FLORA_THIS_FILENAME  'landplane_onground_runwayClosure.flr'
#mode restore
:-(import(from(/(flora_warning_line,1),flrprint))).
?-(catch(abolish_all_tables,_,flora_warning_line('a tabled subgoal depends on ~w.~n		  The program might not function correctly.~n		  Offending file:    ~w~n		  Offending module:  ~w~n', ['\\add', FLORA_THIS_FILENAME, FLORA_THIS_MODULE_NAME]))).
/************************************************************************
  file: syslibinc/flrdynrule_inc.flh

  Author(s): Chang Zhao

  This file is automatically included by the FLORA-2 compiler.
************************************************************************/

:-(compiler_options([xpp_on])).

#mode standard Prolog

#if !defined(FLORA_TERMS_FLH)
#define FLORA_TERMS_FLH
#include "flora_terms.flh"
#endif

?-(:(flrlibman,flora_load_library(FLSYSRULEUPDATE))).

/***********************************************************************/

/************************************************************************
  file: syslibinc/flrdynrule_inc.flh

  Author(s): Chang Zhao

  This file is automatically included by the FLORA-2 compiler.
************************************************************************/

:-(compiler_options([xpp_on])).

#mode standard Prolog

#if !defined(FLORA_TERMS_FLH)
#define FLORA_TERMS_FLH
#include "flora_terms.flh"
#endif

?-(:(flrlibman,flora_load_library(FLSYSRULEUPDATE))).

/***********************************************************************/

#ifndef FLORA_TABLING_METHODS_INCLUDED
#include "flora_tabling_methods.flh"
#endif
 
#if !defined(FLORA_FDB_FILENAME)
#if !defined(FLORA_LOADDYN_DATA)
#define FLORA_LOADDYN_DATA
#endif
#mode save
#mode nocomment "%"
#define FLORA_FDB_FILENAME  'landplane_onground_runwayClosure#for_add.fdb'
#mode restore
?-(:(flrutils,flora_loaddyn_data(FLORA_FDB_FILENAME,FLORA_THIS_MODULE_NAME,'fdb'))).
#else
#if !defined(FLORA_READ_CANONICAL_AND_INSERT)
#define FLORA_READ_CANONICAL_AND_INSERT
#endif
?-(:(flrutils,flora_read_canonical_and_insert(FLORA_FDB_FILENAME,FLORA_THIS_FDB_STORAGE))).
#endif

 
#if !defined(FLORA_FLD_FILENAME)
#if !defined(FLORA_LOADDYN_DATA)
#define FLORA_LOADDYN_DATA
#endif
#mode save
#mode nocomment "%"
#define FLORA_FLD_FILENAME  'landplane_onground_runwayClosure#for_add.fld'
#mode restore
?-(:(flrutils,flora_loaddyn_data(FLORA_FLD_FILENAME,FLORA_THIS_MODULE_NAME,'fld'))).
#else
#if !defined(FLORA_READ_CANONICAL_AND_INSERT)
#define FLORA_READ_CANONICAL_AND_INSERT
#endif
?-(:(flrutils,flora_read_canonical_and_insert(FLORA_FLD_FILENAME,FLORA_THIS_FLD_STORAGE))).
#endif

 
#if !defined(FLORA_FLS_FILENAME)
#if !defined(FLORA_LOADDYN_DATA)
#define FLORA_LOADDYN_DATA
#endif
#mode save
#mode nocomment "%"
#define FLORA_FLS_FILENAME  'landplane_onground_runwayClosure.fls'
#mode restore
?-(:(flrutils,flora_loaddyn_data(FLORA_FLS_FILENAME,FLORA_THIS_MODULE_NAME,'fls'))).
#else
#if !defined(FLORA_READ_CANONICAL_AND_INSERT)
#define FLORA_READ_CANONICAL_AND_INSERT
#endif
?-(:(flrutils,flora_read_symbols_canonical_and_insert(FLORA_FLS_FILENAME,FLORA_THIS_FLS_STORAGE,_SymbolErrNum))).
#endif


%%%%%%%%%%%%%%%%%%%%%%%%%%% Rule insertion statements %%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

?-(','(:(flrwhen,flora_put_attr(__newvar4,'\\$dynrule variable',[=('?aircraft',__aircraft),=('?n',__n),=('?w',__w),=('?wLimit',__wLimit)])),fllibinsertrule_z(FLORA_THIS_MODULE_NAME,[flsysruleupdate('R6',dynrule('landplane_onground_runwayClosure.flr'),'_$_$_flora''descr_vars',4,['_$_$_flora''prop_descriptor'('R6',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME,type,rule,'_$_$_flora''descr_vars'),'_$_$_flora''tag_descriptor'('R6',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME,'R6','_$_$_flora''descr_vars'),'_$_$_flora''rule_enabled'('R6',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME),'_$_$_flora''bool_descriptor'('R6',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME,strict,'_$_$_flora''descr_vars')],[FLORA_THIS_WORKSPACE(d^tblflapply)(littleImportant,'_$ctxt'(_DynRuleCallerModuleVar,'R6',__newcontextvar2))],','(','(','(','(','(FLORA_WORKSPACE(bc,d^mvd)(__newdontcarevar5,interestSpec,__newdontcarevar7,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar9,'R6')),FLORA_WORKSPACE(bc,d^mvd)(__newdontcarevar7,interest,__aircraft,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar8,'R6'))),FLORA_WORKSPACE(bc,d^mvd)(__newdontcarevar5,notam,__n,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar10,'R6'))),FLORA_WORKSPACE(bc,d^isa)(__newdontcarevar5,'SemNOTAMCase','_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar6,'R6'))),','(FLORA_WORKSPACE(bc,d^mvd)(__aircraft,weight,__w,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar11,'R6')),','(FLORA_WORKSPACE(bc,d^mvd)(__n,status,limited,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar12,'R6')),FLORA_WORKSPACE(bc,d^mvd)(__n,weightRestriction,__wLimit,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar13,'R6'))))),fllibdelayedliteral(<,'landplane_onground_runwayClosure.flr',5,[__w,-(__wLimit,1000)])),__newvar3,__newvar4,true,[null],[null],'_$_$_flora''rule_enabled'('R6',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME),[fllibexecute_delayed_calls([__newdontcarevar14,__aircraft,__n,__w,__wLimit],[])],true)]))).
?-(fllibinsertrule_z(FLORA_THIS_MODULE_NAME,[flsysruleupdate('R7',dynrule('landplane_onground_runwayClosure.flr'),'_$_$_flora''descr_vars',5,['_$_$_flora''prop_descriptor'('R7',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME,type,rule,'_$_$_flora''descr_vars'),'_$_$_flora''tag_descriptor'('R7',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME,'R7','_$_$_flora''descr_vars'),'_$_$_flora''rule_enabled'('R7',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME),'_$_$_flora''bool_descriptor'('R7',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME,strict,'_$_$_flora''descr_vars')],[FLORA_THIS_WORKSPACE(d^tblflapply)(highlyImportant,'_$ctxt'(_DynRuleCallerModuleVar,'R7',__newcontextvar2))],','(','(FLORA_WORKSPACE(bc,d^mvd)(__newdontcarevar5,notam,__newdontcarevar7,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar9,'R7')),FLORA_WORKSPACE(bc,d^mvd)(__newdontcarevar7,status,closed,'_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar8,'R7'))),FLORA_WORKSPACE(bc,d^isa)(__newdontcarevar5,'SemNOTAMCase','_$ctxt'(FLORA_THIS_MODULE_NAME,__newcontextvar6,'R7'))),__newvar3,__newvar4,true,[null],[null],'_$_$_flora''rule_enabled'('R7',dynrule('landplane_onground_runwayClosure.flr'),FLORA_THIS_MODULE_NAME),[fllibexecute_delayed_calls([__newdontcarevar10],[])],true)])).


%%%%%%%%%%%%%%%%%%%%%%%%% Signatures for latent queries %%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%




%%%%%%%%%%%%%%%%%%%%%%% Queries found in the source file %%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


 
#if !defined(FLORA_FLS2_FILENAME)
#if !defined(FLORA_LOADDYN_DATA)
#define FLORA_LOADDYN_DATA
#endif
#mode save
#mode nocomment "%"
#define FLORA_FLS2_FILENAME  'landplane_onground_runwayClosure.fls2'
#mode restore
?-(:(flrutils,flora_loaddyn_data(FLORA_FLS2_FILENAME,FLORA_THIS_MODULE_NAME,'fls2'))).
#else
#if !defined(FLORA_READ_CANONICAL_AND_INSERT)
#define FLORA_READ_CANONICAL_AND_INSERT
#endif
?-(:(flrutils,flora_read_symbols_canonical_and_insert(FLORA_FLS2_FILENAME,FLORA_THIS_FLS_STORAGE,_SymbolErrNum))).
#endif

