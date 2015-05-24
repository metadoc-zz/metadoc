## Trees

Class   Seq[Mod], Type.Name, Seq[Type.Param], Ctor.Primary,                                                 body: Template
Trait   Seq[Mod], Type.Name, Seq[Type.Param], Ctor.Primary,                                                 body: Template
Object  Seq[Mod], Term.Name,                  Ctor.Primary,                                                 body: Template 

Type    Seq[Mod], Type.Name, Seq[Type.Param],                                                               body: Type

Val     Seq[Mod], Seq[Pat],                                                          decltpe: Option[Type], body: Term
Var     Seq[Mod], Seq[Pat],                                                          decltpe: Option[Type], body: Option[Term]
Def     Seq[Mod], Term.Name, tparams: Seq[Type.Param], params: Seq[Seq[Term.Param]], decltpe: Option[Type], body: Term

Mod =
  Annot(body: Term)
  Private(Name.Qualifier)
  Protected(Name.Qualifier)
  Implicit
  Final
  Sealed
  Override
  Case
  Abstract
  Covariant
  Contravariant
  Lazy
  ValParam
  VarParam
  Ffi(signature: String) // Foreign function interface: eg Lscala/collection/immutable/List


Ctor.Primary  Seq[Mod], Ctor.Name, Seq[Seq[Term.Param]]
Ctor.Name =
  Ctor.Name         String
  Ctor.Select       Term.Ref, Name
  Ctor.Project      qual: Type, Name
  Ctor.Function     Name

Pat =
  Wildcard
  Bind          lhs: Pat.Var.Term,  rhs: Pat.Arg
  Alternative   lhs: Pat,           rhs: Pat
  Tuple         elements: Seq[Pat]
  Extract       ref: Term.Ref,      targs: Seq[impl.Type],  args: Seq[Pat.Arg]
  ExtractInfix  lhs: Pat,           ref: Term.Name,         rhs: Seq[Pat.Arg]
  Interpolate   prefix: Term.Name,  parts: Seq[Lit.String], args: Seq[Pat]
  Typed         lhs: Pat,           rhs: Pat.Type

Type.Param Seq[Mod], Param.Name, Seq[Type.Param], Type.Bounds, viewBounds: Seq[Type], contextBounds: Seq[Type]
Type =
  Name          String
  Select        Term.Ref          Type.Name
  Project       Type              Type.Name
  Singleton     Term.Ref
  Apply         Type              Seq[Type]
  ApplyInfix    Type              Name                      Type
  Function      Seq[Type.Arg]     Type
  Tuple         Seq[Type] 
  Compound      Seq[Type]         refinement: Seq[Stat]
  Existential   Type              quants: Seq[Stat]
  Annotate      Type              Seq[Mod.Annot]
  Placeholder   Bounds
  Bounds        lo: Option[Type]  hi: Option[Type]

Type.Arg =
    ByName Type
    Repeated Type
 
Term.Ref = 
  Term.This    Name.Qualifier
  Term.Super   Name.Qualifier, superp: Name.Qualifier
  Term.Name    String
  Term.Select  Term, Term.Name

Template = early: Seq[Stat] parents: Seq[Term] self: Term.Param stats: Option[Seq[Stat]]

Stat = ???

Term.Param = Seq[Mod] Param.Name, decltpe: Option[Type.Arg], default: Option[Term]
Term = 
  This Name.Qualifier
  Super thisp: impl.Name.Qualifier superp: impl.Name.Qualifier
  Name String
  Select Term Term.Name
  Interpolate prefix: Name parts: Seq[Lit.String] args: Seq[Term]
  Apply fun: Term args: Seq[Arg]
  ApplyType fun: Term targs: Seq[Type]
  ApplyInfix lhs: Term op: Name targs: Seq[Type] args: Seq[Arg]
  ApplyUnary op: Name arg: Term
  Assign lhs: Term.Ref rhs: Term
  Update fun: Term Seq[Seq[Arg]] rhs: Term
  Return Term
  Throw Term
  Ascribe Term Type
  Annotate Term Seq[Mod.Annot]
  Tuple Seq[Term]
  Block Seq[Stat]
  If Term Term Term
  Match Term Seq[Case]
  TryWithCases Term, catchp: Seq[Case], finallyp: Option[Term]
  TryWithTerm Term, catchp: Term, finallyp: Option[Term]
  Function Seq[Term.Param] Term
  PartialFunction Seq[Case]
  While Term Term
  Do Term Term
  For Seq[Enumerator] Term
  ForYield Seq[Enumerator] Term
  New Template
  Placeholder
  Eta Term
  Param Seq[Mod] Param.Name decltpe: Option[Type.Arg] default: Option[Term]

## Sematic

### Attributes

desugar: Term => Term
tpe: Term => Type
tpe: Type => Type ?
tpe: Member => Type

dearg: Member, Type.Arg => Type

defns: Ref => Seq[Member] ?
defn: Ref => Member

### Types

Type <:< Type => Boolean
Type =:= Type => Boolean
widen: Type => Type
dealias: Type => Type
parents: Type => Seq[Type]

tpes: Seq[Type] => Type
tpes: Seq[Type] => Type

companion: X => Member.Type


## Tokens