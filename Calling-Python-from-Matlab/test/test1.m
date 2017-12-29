%if count(py.sys.path,'') == 0
    %insert(py.sys.path,int32(0),'');
    %a=0
%end

% [own_path, ~, ~] = fileparts(mfilename('fullpath'));
% module_path = fullfile(own_path, 'mymod.py');
% python_path = py.sys.path;
% if count(python_path, module_path) == 0
%     insert(python_path, int32(0), module_path);
% end

P = py.sys.path;
if count(P,'C:\Users\longshao\Desktop\DL planning\Calling-Python-from-Matlab-master\test\mymod.py') == 0
    insert(P,int32(0),'C:\Users\longshao\Desktop\DL planning\Calling-Python-from-Matlab-master\test\mymod.py');
end

%Create an input argument, a list of names, in MATLAB.
N = py.list({'Jones','Johnson','James'})
ss = py.sys.path
M  = py.mymod.search()
