-- generated by newgenasym Sun Jun 10 11:48:17 2007

library ieee;
use     ieee.std_logic_1164.all;
use     work.all;
entity ac_hc_32_v1 is
    generic (
        size:positive:= 1
            );
    port (    
	A:         IN     STD_LOGIC_VECTOR (size-1 DOWNTO 0);    
	B:         IN     STD_LOGIC_VECTOR (size-1 DOWNTO 0);    
	O:         OUT    STD_LOGIC_VECTOR (size-1 DOWNTO 0));
end ac_hc_32_v1;
