CREATE DATABASE ppi;
use ppi;

CREATE TABLE `protein` (
   `Accession` varchar(100) not null,
   `Entry_name` varchar(100),
   `Status` varchar(25),
   `Protein_name` varchar(30),
   `Gene_names` varchar(100),
   `Length_` int(11) not null,
   `Protein_existence` varchar(25),
   `Date_of_modification` varchar(40),
   `Pubmed_Id` int(11) not null,
   `Gene_location` varchar(25),
   `Protein_family` varchar(40),
   PRIMARY KEY (`Accession`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `protein` (`Entry_name`, `Status`, `Protein_name`, `Gene_names`, `Accession`, `Length_`, `Protein_existence`, `Date_of_modification`, `Pubmed_Id`, `Gene_location`, `Protein_family`) VALUES 
('Q14342_HUMAN', 'unreviewed', 'FYN protein (Fragment)', 'FYN', 'Q14342', '0','Predicted','23-03-10','3287380','','');

CREATE TABLE `interactions` (
   `Id_interactor_A` varchar(100) not null,
   `Id_interactor_B` varchar(100) not null,
   `Interaction_type` varchar(40),
   `Publication_identifier` int(11),
   PRIMARY KEY (`Id_interactor_A`) REFERENCES protein(`Accession`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `interactions` (`Id_interactor_A`, `Id_interactor_B`, `Interaction_type`, `Publication_identifier`) VALUES 
('P04150', 'Q14342', 'MI:0403(colocalization)', '16888650'),
('P04234', 'Q14342', 'MI:0915(physical association)', '16888650');